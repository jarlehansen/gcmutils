package net.jarlehansen.android.gcm.manifest.xml.android.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:04 AM
 */
@Root(name = "manifest", strict = false)
public class AndroidManifest {
    @Element(name = "uses-sdk", required = false)
    private UsesSdk usesSdk = null;

    @ElementList(entry = "permission", inline = true, required = false)
    private List<Permission> permissions = null;

    @ElementList(entry = "uses-permission", inline = true, required = false)
    private List<UsesPermission> usesPermissions = null;

    @Element(required = false)
    private Application application = null;

    public UsesSdk getUsesSdk() {
        return usesSdk;
    }

    public void setUsesSdk(UsesSdk usesSdk) {
        this.usesSdk = usesSdk;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void addPermission(Permission permission) {
        if (permissions == null)
            permissions = new ArrayList<Permission>();

        permissions.add(permission);
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void addUsesPermission(UsesPermission usesPermission) {
        if (usesPermissions == null)
            usesPermissions = new ArrayList<UsesPermission>();

        usesPermissions.add(usesPermission);
    }

    public List<UsesPermission> getUsesPermissions() {
        return usesPermissions;
    }

    public void setUsesPermissions(List<UsesPermission> usesPermissions) {
        this.usesPermissions = usesPermissions;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "AndroidManifest{" +
                "usesSdk=" + usesSdk +
                ", permissions=" + permissions +
                ", usesPermissions=" + usesPermissions +
                ", application=" + application +
                '}';
    }
}
