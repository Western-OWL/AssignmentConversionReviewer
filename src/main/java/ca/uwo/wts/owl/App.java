package ca.uwo.wts.owl;

import ca.uwo.wts.owl.data.AssignmentAssignment;
import ca.uwo.wts.owl.data.AssignmentContent;
import ca.uwo.wts.owl.data.AssignmentSubmission;
import ca.uwo.wts.owl.data.SakaiSiteGroup;
import ca.uwo.wts.owl.data.SakaiUserIdMap;
import ca.uwo.wts.owl.data.SakaiRealm;
import ca.uwo.wts.owl.data.SakaiRealmRlGr;
import ca.uwo.wts.owl.data.SakaiRealmRole;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;

/**
 * This application parses a file containing the tomcat log output from the Assignments conversion job, and outputs lines that require investigation.
 * That is, we seek for lines that have known causes which do not require any additional work, and then *don't* print them. Everything that is unknown is printed.
 */
public class App 
{
    public static void main( String[] args )
    {
        String filename = "assignmentconversion.out";
        try (BufferedReader bf = Files.newBufferedReader(Paths.get(filename)))
        {
            String line = bf.readLine();
            String logLine;
            while (line != null)
            {
                logLine = line;
                line = bf.readLine();
                while (line != null && !startsWithADate(line))
                {
                    logLine = logLine + "\n" + line;
                    line = bf.readLine();
                }

                if (!logLine.contains("org.sakaiproject.assignment.impl.conversion"))
                {
                    // this isn't output from the job; skip
                }
                else if (logLine.contains("<====="))
                {
                    // all these occurrences are information about the job's configuration / progress / statistics, but not useful for this script; skip
                }
                else
                {
                    if (logLine.contains("reintegration of submission"))
                    {
                        // let's find out if we care!
                        String submissionId = logLine.substring(169, 205);
                        String assignmentId = logLine.substring(220, 256);
                        try
                        {
                            if (!isSubmissionInteresting(submissionId, assignmentId))
                            {
                                // boring... next logLine! (doesn't require investigation)
                                continue;
                            }
                        }
                        catch (RuntimeException e)
                        {
                            System.out.println("Uncertain how to proceed. " + e.getMessage() + " The log line:");
                        }
                    }
                    else if (logLine.contains("xml is invalid skipping assignment"))
                    {
                        String assignmentId = logLine.substring(196, 232);
                        // Does it have an associated assignmentContent?
                        if (!assignmentContentExistsForAssignment(assignmentId))
                        {
                            // The assignment has no associated assignment content
                            continue;
                        }
                    }
                    // Line must be investigated
                    System.out.println(logLine);
                }

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean startsWithADate(String line)
    {
        DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
        try
        {
            sdf.parse(line.substring(0, 24));
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Returns true if and only if the given submission requires a manual investigation.
     */
    public static boolean isSubmissionInteresting(String submissionId, String assignmentId)
    {
        AssignmentSubmission submission = getSubmission(submissionId);
        AssignmentAssignment assignment = getAssignment(assignmentId);
        String assignmentXml = assignment.getXml();
        if (assignmentXml.contains(" group=\"true\" "))
        {
            // It's a group assignment
            String submitterId = submission.getSubmitterId();
            if (userExists(submitterId))
            {
                // It's a user submission on a group assignment. Not interesting! Skip
                return false;
            }
            else
            {
                // SubmitterId is not a user. Is it a group?
                if (groupExists(submitterId))
                {
                    // Is the group in the assignmentXML's authzGroups?
                    if (!xmlContainsAuthzGroupTagForSubmitterId(assignmentXml, submitterId))
                    {
                        // The submitterId is a group, but the assignment isn't actually assigned to this group; skip
                        return false;
                    }

                    // submitterId is a group, and it's in the assignment's assigned groups. Does the group's realm contain students (/guests)?
                    // note - throws an exception if there are multiple group realms matching this submitterId; handled in the caller
                    if (!groupRealmContainsStudents(submitterId))
                    {
                        // Group doesn't contain students; not interesting
                        return false;
                    }

                    // It's a group assignment assigned to a group that exists with students... yet reintegration failed. How interesting!
                    return true;
                }
                else
                {
                    // The submitter is neither a student, nor a group. Something must have been deleted. Skip
                    return false;
                }
            }
        }

        // This case isn't explained by any of the paths above. How interesting!
        return true;
    }

    public static AssignmentSubmission getSubmission(String submissionId)
    {
        ObjectSelect<AssignmentSubmission> submissionQuery = ObjectSelect.query(AssignmentSubmission.class).where(AssignmentSubmission.SUBMISSION_ID.eq(submissionId));
        return submissionQuery.selectFirst(ObjectContextProvider.getReadContext());
    }

    public static AssignmentAssignment getAssignment(String assignmentId)
    {
        ObjectSelect<AssignmentAssignment> assignmentQuery = ObjectSelect.query(AssignmentAssignment.class).where(AssignmentAssignment.ASSIGNMENT_ID.eq(assignmentId));
        return assignmentQuery.selectFirst(ObjectContextProvider.getReadContext());
    }

    public static boolean xmlContainsAuthzGroupTagForSubmitterId(String assignmentXml, String submitterId)
    {
        /* 
         * Find occurrences of <group authzGroup="...submitterId. Method:
         * Starting from index 0:
         *   Search via 'indexOf' starting at the index to an occurrence of the submitterId in the XML. 
         *     If found, backtrack to an occurrence of "<" and identify if it matches "<group authzGroup=\""
         *         If so, return true
         *     Otherwise, advance the index and repeat
         * Return false if our search hits the end (I.e. indexOf submitterId returns -1)
         */
        int index = 0;
        while (index != -1)
        {   
            index = assignmentXml.indexOf(submitterId, index);
            if (index != -1)
            {   
                int bracketIndex = assignmentXml.lastIndexOf("<", index);
                String toMatch = "<group authzGroup=\"";
                String tag = assignmentXml.substring(bracketIndex, bracketIndex + toMatch.length());
                if (tag.equals(toMatch))
                {
                    return true;
                }
                index++;
            }
        }
        return false;
    }

    public static boolean userExists(String userId)
    {
        ObjectSelect<SakaiUserIdMap> userQuery = ObjectSelect.query(SakaiUserIdMap.class).where(SakaiUserIdMap.USER_ID.eq(userId));
        return userQuery.selectFirst(ObjectContextProvider.getReadContext()) != null;
    }

    public static boolean groupExists(String groupId)
    {
        ObjectSelect<SakaiSiteGroup> groupQuery = ObjectSelect.query(SakaiSiteGroup.class).where(SakaiSiteGroup.GROUP_ID.eq(groupId));
        return groupQuery.selectFirst(ObjectContextProvider.getReadContext()) != null;
    }

    /**
     * Queries the group, grabs the Student / Guest roles, and tries to find matching realm-role mappings. 
     * If multiple groups match this ID, a RuntimeException is thrown (this shouldn't happen, but I'm just not 100% certain)
     */
    public static boolean groupRealmContainsStudents(String groupId)
    {
        ObjectSelect<SakaiRealm> realmQuery = ObjectSelect.query(SakaiRealm.class).where(SakaiRealm.REALM_ID.like("%/" + groupId));
        List<SakaiRealm> realms = realmQuery.select(ObjectContextProvider.getReadContext());
        if (realms.isEmpty())
        {
            // No group realm, therefore no students
            return false;
        }
        if (realms.size() > 1)
        {
            // Throwing an exception with a message narrows things down, making the investigation easier
            throw new RuntimeException("Multiple group realms identified matching this groupId: " + groupId + ".");
        }

        SakaiRealm realm = realms.get(0);

        List<String> roleNames = new ArrayList<>();
        roleNames.add("Student");
        roleNames.add("Guest");
        List<SakaiRealmRole> roles = getRoles(roleNames);

        // Return true if there are any realm role mappings that map the group's realm to a student/guest role
        return !getRoleMappingsForRealmWithRoles(realm, roles).isEmpty();
    }

    public static List<SakaiRealmRole> getRoles(List<String> roleNames)
    {
        ObjectSelect<SakaiRealmRole> roleQuery = ObjectSelect.query(SakaiRealmRole.class).where(SakaiRealmRole.ROLE_NAME.in(roleNames));
        return roleQuery.select(ObjectContextProvider.getReadContext());
    }

    public static List<SakaiRealmRlGr> getRoleMappingsForRealmWithRoles(SakaiRealm realm, List<SakaiRealmRole> roles)
    {
        // Query: select * from sakai_realm_rl_gr where realm_key = <realm key> and role_key in (<list of role keys>);
        Long realmKey = realm.getRealmKey();
        ObjectSelect<SakaiRealmRlGr> realmRlGrQuery = ObjectSelect.query(SakaiRealmRlGr.class)
            .where(SakaiRealmRlGr.REALM_KEY.eq(realm.getRealmKey()))
            .and(SakaiRealmRlGr.ROLE_KEY.in(
                roles.stream().map(SakaiRealmRole::getRoleKey).collect(Collectors.toList())));
        return realmRlGrQuery.select(ObjectContextProvider.getReadContext());
    }

    public static boolean assignmentContentExistsForAssignment(String assignmentId)
    {
        AssignmentAssignment assignment = getAssignment(assignmentId);
        String xml = assignment.getXml();

        // Isolate the contentId from the assignment's XML:
        // XML's contentId is typically formatted as: assignmentcontent="/assignment/c/<site ID>/<contentId>"
        String toMatch = " assignmentcontent=\"";
        // Select the index of assignmentcontent's first quote
        int contentIdFirstQuoteIndex = xml.indexOf(toMatch) + toMatch.length() - 1;
        // From that index search for the index of the second quote
        int contentIdSecondQuoteIndex = xml.indexOf('\"', contentIdFirstQuoteIndex + 1);
        // The contentID should be the last 36 characters within these quotes, however, it can be an empty string.
        // So substring from max(first quote, second quote - 36 characters) to the second quote
        String contentId = xml.substring(Math.max(contentIdFirstQuoteIndex + 1, contentIdSecondQuoteIndex - 36), contentIdSecondQuoteIndex);

        if ("".equals(contentId))
        {
            // ContentId doesn't exist
            return false;
        }

        ObjectSelect<AssignmentContent> contentQuery = ObjectSelect.query(AssignmentContent.class).where(AssignmentContent.CONTENT_ID.eq(contentId));
        return contentQuery.selectFirst(ObjectContextProvider.getReadContext()) != null;
    }
}
