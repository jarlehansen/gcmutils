package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.UsesSdk;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 9:40 AM
 */
public class UsesSdkImplementorTest {
    private UsesSdkImplementor implementor;
    private AndroidManifest manifest;
    private XmlParts xmlParts;

    @Before
    public void setUp() {
        manifest = new AndroidManifest();
        xmlParts = new XmlParts();
        implementor = new UsesSdkImplementor();
    }

    @Test
    public void populateXml_minSdkExists() {
        UsesSdk usesSdk = new UsesSdk();
        usesSdk.setMinSdkVersion("123");

        manifest.setUsesSdk(usesSdk);

        implementor.populateXmlParts(xmlParts, manifest);

        assertTrue(xmlParts.containsUsesSdk());
    }

    @Test
    public void populateXml_minSdkMissing() {
        UsesSdk usesSdk = new UsesSdk();
        usesSdk.setMinSdkVersion("");

        manifest.setUsesSdk(usesSdk);

        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsUsesSdk());
    }

    @Test
    public void populateXml_minSdkNull() {
        UsesSdk usesSdk = new UsesSdk();
        usesSdk.setMinSdkVersion(null);

        manifest.setUsesSdk(usesSdk);

        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsUsesSdk());
    }

    @Test
    public void populateXml_nullUsesSdk() {
        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsUsesSdk());
    }
}
