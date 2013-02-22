package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 10:19 AM
 */
public class PermissionImplementor implements XmlImplementor {

    @Override
    public void populateXmlParts(XmlParts xmlParts, AndroidManifest androidManifest) {
        List<Permission> permissions = androidManifest.getPermissions();
        if (permissions == null) {
            permissions = new ArrayList<Permission>();
        }

        boolean containsPermission = false;
        for (Permission permission : permissions) {
            String name = permission.getName();

            if (name != null && name.endsWith(XmlKeys.PERMISSION)) {
                containsPermission = true;
                break;
            }
        }

        xmlParts.setPermission(containsPermission);
    }
}
