package ca.uwo.wts.owl.data.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import ca.uwo.wts.owl.data.SakaiRealm;
import ca.uwo.wts.owl.data.SakaiRealmRlGr;

/**
 * Class _SakaiRealmRole was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _SakaiRealmRole extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ROLE_KEY_PK_COLUMN = "ROLE_KEY";

    public static final Property<String> ROLE_NAME = Property.create("roleName", String.class);
    public static final Property<Long> ROLE_KEY = Property.create("roleKey", Long.class);
    public static final Property<List<SakaiRealm>> SAKAI_REALMS = Property.create("sakaiRealms", List.class);
    public static final Property<List<SakaiRealmRlGr>> SAKAI_REALM_RL_GRS = Property.create("sakaiRealmRlGrs", List.class);

    protected String roleName;
    protected Long roleKey;

    protected Object sakaiRealms;
    protected Object sakaiRealmRlGrs;

    public String getRoleName() {
        beforePropertyRead("roleName");
        return this.roleName;
    }

    public Long getRoleKey() {
        beforePropertyRead("roleKey");
        return this.roleKey;
    }

    public void addToSakaiRealms(SakaiRealm obj) {
        addToManyTarget("sakaiRealms", obj, true);
    }

    public void removeFromSakaiRealms(SakaiRealm obj) {
        removeToManyTarget("sakaiRealms", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<SakaiRealm> getSakaiRealms() {
        return (List<SakaiRealm>)readProperty("sakaiRealms");
    }

    public void addToSakaiRealmRlGrs(SakaiRealmRlGr obj) {
        addToManyTarget("sakaiRealmRlGrs", obj, true);
    }

    public void removeFromSakaiRealmRlGrs(SakaiRealmRlGr obj) {
        removeToManyTarget("sakaiRealmRlGrs", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<SakaiRealmRlGr> getSakaiRealmRlGrs() {
        return (List<SakaiRealmRlGr>)readProperty("sakaiRealmRlGrs");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "roleName":
                return this.roleName;
            case "roleKey":
                return this.roleKey;
            case "sakaiRealms":
                return this.sakaiRealms;
            case "sakaiRealmRlGrs":
                return this.sakaiRealmRlGrs;
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
            case "roleName":
                this.roleName = (String)val;
                break;
            case "roleKey":
                this.roleKey = (Long)val;
                break;
            case "sakaiRealms":
                this.sakaiRealms = val;
                break;
            case "sakaiRealmRlGrs":
                this.sakaiRealmRlGrs = val;
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
        out.writeObject(this.roleName);
        out.writeObject(this.roleKey);
        out.writeObject(this.sakaiRealms);
        out.writeObject(this.sakaiRealmRlGrs);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.roleName = (String)in.readObject();
        this.roleKey = (Long)in.readObject();
        this.sakaiRealms = in.readObject();
        this.sakaiRealmRlGrs = in.readObject();
    }

}
