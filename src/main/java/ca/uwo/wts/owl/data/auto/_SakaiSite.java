package ca.uwo.wts.owl.data.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import ca.uwo.wts.owl.data.SakaiSiteGroup;

/**
 * Class _SakaiSite was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _SakaiSite extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String SITE_ID_PK_COLUMN = "SITE_ID";

    public static final Property<String> TITLE = Property.create("title", String.class);
    public static final Property<String> TYPE = Property.create("type", String.class);
    public static final Property<String> SHORT_DESC = Property.create("shortDesc", String.class);
    public static final Property<String> DESCRIPTION = Property.create("description", String.class);
    public static final Property<String> ICON_URL = Property.create("iconUrl", String.class);
    public static final Property<String> INFO_URL = Property.create("infoUrl", String.class);
    public static final Property<String> SKIN = Property.create("skin", String.class);
    public static final Property<Integer> PUBLISHED = Property.create("published", Integer.class);
    public static final Property<String> JOINABLE = Property.create("joinable", String.class);
    public static final Property<String> PUBVIEW = Property.create("pubview", String.class);
    public static final Property<String> JOIN_ROLE = Property.create("joinRole", String.class);
    public static final Property<String> CREATEDBY = Property.create("createdby", String.class);
    public static final Property<String> MODIFIEDBY = Property.create("modifiedby", String.class);
    public static final Property<LocalDateTime> CREATEDON = Property.create("createdon", LocalDateTime.class);
    public static final Property<LocalDateTime> MODIFIEDON = Property.create("modifiedon", LocalDateTime.class);
    public static final Property<String> IS_SPECIAL = Property.create("isSpecial", String.class);
    public static final Property<String> IS_USER = Property.create("isUser", String.class);
    public static final Property<String> CUSTOM_PAGE_ORDERED = Property.create("customPageOrdered", String.class);
    public static final Property<String> IS_SOFTLY_DELETED = Property.create("isSoftlyDeleted", String.class);
    public static final Property<LocalDateTime> SOFTLY_DELETED_DATE = Property.create("softlyDeletedDate", LocalDateTime.class);
    public static final Property<List<SakaiSiteGroup>> SAKAI_SITE_GROUPS = Property.create("sakaiSiteGroups", List.class);

    protected String title;
    protected String type;
    protected String shortDesc;
    protected String description;
    protected String iconUrl;
    protected String infoUrl;
    protected String skin;
    protected Integer published;
    protected String joinable;
    protected String pubview;
    protected String joinRole;
    protected String createdby;
    protected String modifiedby;
    protected LocalDateTime createdon;
    protected LocalDateTime modifiedon;
    protected String isSpecial;
    protected String isUser;
    protected String customPageOrdered;
    protected String isSoftlyDeleted;
    protected LocalDateTime softlyDeletedDate;

    protected Object sakaiSiteGroups;

    public String getTitle() {
        beforePropertyRead("title");
        return this.title;
    }

    public String getType() {
        beforePropertyRead("type");
        return this.type;
    }

    public String getShortDesc() {
        beforePropertyRead("shortDesc");
        return this.shortDesc;
    }

    public String getDescription() {
        beforePropertyRead("description");
        return this.description;
    }

    public String getIconUrl() {
        beforePropertyRead("iconUrl");
        return this.iconUrl;
    }

    public String getInfoUrl() {
        beforePropertyRead("infoUrl");
        return this.infoUrl;
    }

    public String getSkin() {
        beforePropertyRead("skin");
        return this.skin;
    }

    public int getPublished() {
        beforePropertyRead("published");
        if(this.published == null) {
            return 0;
        }
        return this.published;
    }

    public String getJoinable() {
        beforePropertyRead("joinable");
        return this.joinable;
    }

    public String getPubview() {
        beforePropertyRead("pubview");
        return this.pubview;
    }

    public String getJoinRole() {
        beforePropertyRead("joinRole");
        return this.joinRole;
    }

    public String getCreatedby() {
        beforePropertyRead("createdby");
        return this.createdby;
    }

    public String getModifiedby() {
        beforePropertyRead("modifiedby");
        return this.modifiedby;
    }

    public LocalDateTime getCreatedon() {
        beforePropertyRead("createdon");
        return this.createdon;
    }

    public LocalDateTime getModifiedon() {
        beforePropertyRead("modifiedon");
        return this.modifiedon;
    }

    public String getIsSpecial() {
        beforePropertyRead("isSpecial");
        return this.isSpecial;
    }

    public String getIsUser() {
        beforePropertyRead("isUser");
        return this.isUser;
    }

    public String getCustomPageOrdered() {
        beforePropertyRead("customPageOrdered");
        return this.customPageOrdered;
    }

    public String getIsSoftlyDeleted() {
        beforePropertyRead("isSoftlyDeleted");
        return this.isSoftlyDeleted;
    }

    public LocalDateTime getSoftlyDeletedDate() {
        beforePropertyRead("softlyDeletedDate");
        return this.softlyDeletedDate;
    }

    public void addToSakaiSiteGroups(SakaiSiteGroup obj) {
        addToManyTarget("sakaiSiteGroups", obj, true);
    }

    public void removeFromSakaiSiteGroups(SakaiSiteGroup obj) {
        removeToManyTarget("sakaiSiteGroups", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<SakaiSiteGroup> getSakaiSiteGroups() {
        return (List<SakaiSiteGroup>)readProperty("sakaiSiteGroups");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "title":
                return this.title;
            case "type":
                return this.type;
            case "shortDesc":
                return this.shortDesc;
            case "description":
                return this.description;
            case "iconUrl":
                return this.iconUrl;
            case "infoUrl":
                return this.infoUrl;
            case "skin":
                return this.skin;
            case "published":
                return this.published;
            case "joinable":
                return this.joinable;
            case "pubview":
                return this.pubview;
            case "joinRole":
                return this.joinRole;
            case "createdby":
                return this.createdby;
            case "modifiedby":
                return this.modifiedby;
            case "createdon":
                return this.createdon;
            case "modifiedon":
                return this.modifiedon;
            case "isSpecial":
                return this.isSpecial;
            case "isUser":
                return this.isUser;
            case "customPageOrdered":
                return this.customPageOrdered;
            case "isSoftlyDeleted":
                return this.isSoftlyDeleted;
            case "softlyDeletedDate":
                return this.softlyDeletedDate;
            case "sakaiSiteGroups":
                return this.sakaiSiteGroups;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "title":
                this.title = (String)val;
                break;
            case "type":
                this.type = (String)val;
                break;
            case "shortDesc":
                this.shortDesc = (String)val;
                break;
            case "description":
                this.description = (String)val;
                break;
            case "iconUrl":
                this.iconUrl = (String)val;
                break;
            case "infoUrl":
                this.infoUrl = (String)val;
                break;
            case "skin":
                this.skin = (String)val;
                break;
            case "published":
                this.published = (Integer)val;
                break;
            case "joinable":
                this.joinable = (String)val;
                break;
            case "pubview":
                this.pubview = (String)val;
                break;
            case "joinRole":
                this.joinRole = (String)val;
                break;
            case "createdby":
                this.createdby = (String)val;
                break;
            case "modifiedby":
                this.modifiedby = (String)val;
                break;
            case "createdon":
                this.createdon = (LocalDateTime)val;
                break;
            case "modifiedon":
                this.modifiedon = (LocalDateTime)val;
                break;
            case "isSpecial":
                this.isSpecial = (String)val;
                break;
            case "isUser":
                this.isUser = (String)val;
                break;
            case "customPageOrdered":
                this.customPageOrdered = (String)val;
                break;
            case "isSoftlyDeleted":
                this.isSoftlyDeleted = (String)val;
                break;
            case "softlyDeletedDate":
                this.softlyDeletedDate = (LocalDateTime)val;
                break;
            case "sakaiSiteGroups":
                this.sakaiSiteGroups = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.title);
        out.writeObject(this.type);
        out.writeObject(this.shortDesc);
        out.writeObject(this.description);
        out.writeObject(this.iconUrl);
        out.writeObject(this.infoUrl);
        out.writeObject(this.skin);
        out.writeObject(this.published);
        out.writeObject(this.joinable);
        out.writeObject(this.pubview);
        out.writeObject(this.joinRole);
        out.writeObject(this.createdby);
        out.writeObject(this.modifiedby);
        out.writeObject(this.createdon);
        out.writeObject(this.modifiedon);
        out.writeObject(this.isSpecial);
        out.writeObject(this.isUser);
        out.writeObject(this.customPageOrdered);
        out.writeObject(this.isSoftlyDeleted);
        out.writeObject(this.softlyDeletedDate);
        out.writeObject(this.sakaiSiteGroups);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.title = (String)in.readObject();
        this.type = (String)in.readObject();
        this.shortDesc = (String)in.readObject();
        this.description = (String)in.readObject();
        this.iconUrl = (String)in.readObject();
        this.infoUrl = (String)in.readObject();
        this.skin = (String)in.readObject();
        this.published = (Integer)in.readObject();
        this.joinable = (String)in.readObject();
        this.pubview = (String)in.readObject();
        this.joinRole = (String)in.readObject();
        this.createdby = (String)in.readObject();
        this.modifiedby = (String)in.readObject();
        this.createdon = (LocalDateTime)in.readObject();
        this.modifiedon = (LocalDateTime)in.readObject();
        this.isSpecial = (String)in.readObject();
        this.isUser = (String)in.readObject();
        this.customPageOrdered = (String)in.readObject();
        this.isSoftlyDeleted = (String)in.readObject();
        this.softlyDeletedDate = (LocalDateTime)in.readObject();
        this.sakaiSiteGroups = in.readObject();
    }

}
