package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Permission;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 10:22 AM
 */
public class PermissionImplementorTest {
    private PermissionImplementor implementor;
    private AndroidManifest manifest;
    private XmlParts xmlParts;

    @Before
    public void setUp() {
        manifest = new AndroidManifest();
        xmlParts = new XmlParts();

        implementor = new PermissionImplementor();
    }

    @Test
    public void populateXml_nullPermissions() {
        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsPermission());
    }

    @Test
    public void populateXml_permissionExist() {
        Permission permission1 = new Permission();
        permission1.setName("test-permission");
        permission1.setProtectionLevel("test-protection-level");

        Permission permission2 = new Permission();
        permission2.setName(XmlKeys.PERMISSION);

        manifest.addPermission(permission1);
        manifest.addPermission(permission2);

        implementor.populateXmlParts(xmlParts, manifest);

        assertTrue(xmlParts.containsPermission());
    }

    @Test
    public void populateXml_wrongPermissionExists() {
        Permission permission1 = new Permission();
        permission1.setName("test-permission");
        permission1.setProtectionLevel("test-protection-level");

        AndroidManifest manifest = new AndroidManifest();
        manifest.addPermission(permission1);

        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsPermission());
    }
}
