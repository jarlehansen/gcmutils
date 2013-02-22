package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.UsesPermission;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 12:38 PM
 */
public class UsesPermissionImplementorTest {
    private UsesPermissionImplementor implementor;
    private AndroidManifest manifest;
    private XmlParts xmlParts;

    @Before
    public void setUp() {
        manifest = new AndroidManifest();
        xmlParts = new XmlParts();
        implementor = new UsesPermissionImplementor();
    }

    @Test
    public void populateXml_nullUsesPermission() {
        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsUsesPermission1());
        assertFalse(xmlParts.containsUsesPermission2());
        assertFalse(xmlParts.containsUsesPermission3());
        assertFalse(xmlParts.containsUsesPermission4());
        assertFalse(xmlParts.containsUsesPermission5());
    }

    @Test
    public void populateXml_twoUsesPermissionExist() {
        manifest.addUsesPermission(new UsesPermission(XmlKeys.USES_PERMISSION_4));
        manifest.addUsesPermission(new UsesPermission(XmlKeys.USES_PERMISSION_5));

        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsUsesPermission1());
        assertFalse(xmlParts.containsUsesPermission2());
        assertFalse(xmlParts.containsUsesPermission3());
        assertTrue(xmlParts.containsUsesPermission4());
        assertTrue(xmlParts.containsUsesPermission5());
    }

    @Test
    public void populateXml_usesPermissionsExist() {
        manifest.addUsesPermission(new UsesPermission(XmlKeys.USES_PERMISSION_1));
        manifest.addUsesPermission(new UsesPermission(XmlKeys.USES_PERMISSION_2));
        manifest.addUsesPermission(new UsesPermission(XmlKeys.USES_PERMISSION_3));
        manifest.addUsesPermission(new UsesPermission(XmlKeys.USES_PERMISSION_4));
        manifest.addUsesPermission(new UsesPermission(XmlKeys.USES_PERMISSION_5));
        manifest.addUsesPermission(new UsesPermission("test-permission1"));
        manifest.addUsesPermission(new UsesPermission("test-permission2"));

        implementor.populateXmlParts(xmlParts, manifest);

        assertTrue(xmlParts.containsUsesPermission1());
        assertTrue(xmlParts.containsUsesPermission2());
        assertTrue(xmlParts.containsUsesPermission3());
        assertTrue(xmlParts.containsUsesPermission4());
        assertTrue(xmlParts.containsUsesPermission5());
    }
}