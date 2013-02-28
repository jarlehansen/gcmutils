package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.UsesPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 12:34 PM
 */
public class UsesPermissionImplementor implements XmlImplementor {

    public void populateXmlParts(XmlParts xmlParts, AndroidManifest androidManifest) {
        List<UsesPermission> usesPermissions = androidManifest.getUsesPermissions();
        if (usesPermissions == null)
            usesPermissions = new ArrayList<UsesPermission>();

        boolean containsPermission1 = false;
        boolean containsPermission2 = false;
        boolean containsPermission3 = false;
        boolean containsPermission4 = false;
        boolean containsPermission5 = false;

        for (UsesPermission usesPermission : usesPermissions) {
            String name = usesPermission.getName();
            if (name != null && name.endsWith(XmlKeys.USES_PERMISSION_1))
                containsPermission1 = true;
            if (XmlKeys.USES_PERMISSION_2.equals(name))
                containsPermission2 = true;
            else if (XmlKeys.USES_PERMISSION_3.equals(name))
                containsPermission3 = true;
            else if (XmlKeys.USES_PERMISSION_4.equals(name))
                containsPermission4 = true;
            else if (XmlKeys.USES_PERMISSION_5.equals(name))
                containsPermission5 = true;
        }

        xmlParts.setUsesPermission1(containsPermission1);
        xmlParts.setUsesPermission2(containsPermission2);
        xmlParts.setUsesPermission3(containsPermission3);
        xmlParts.setUsesPermission4(containsPermission4);
        xmlParts.setUsesPermission5(containsPermission5);
    }
}
