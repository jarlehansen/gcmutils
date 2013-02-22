package net.jarlehansen.android.gcm.manifest;

import net.jarlehansen.android.gcm.manifest.xml.android.AndroidManifestDeserializer;
import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.parts.XmlParts;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/22/13
 * Time: 7:32 AM
 */
public class AndroidManifestServiceIT {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File outputFile;

    @Before
    public void setUp() throws IOException {
        outputFile = folder.newFile();
    }

    @Test
    public void execute_noGCMManifest() {
        AndroidManifestService service = new AndroidManifestService("src/test/resources/NoGCM-AndroidManifest.xml", outputFile.getAbsolutePath(),
                "net.jarlehansen.android.gcm");
        service.execute();

        AndroidManifestDeserializer deserializer = new AndroidManifestDeserializer(outputFile);
        AndroidManifest manifest = deserializer.deserialize();

        XmlParts xmlParts = service.populateXmlParts(manifest);

        assertCompleteManifest(xmlParts);
    }

    @Test
    public void execute_partialManifest() {

        AndroidManifestService service = new AndroidManifestService("src/test/resources/Partial-AndroidManifest.xml", outputFile.getAbsolutePath(),
                "net.jarlehansen.android.gcm");
        service.execute();

        AndroidManifestDeserializer deserializer = new AndroidManifestDeserializer(outputFile);
        AndroidManifest manifest = deserializer.deserialize();

        XmlParts xmlParts = service.populateXmlParts(manifest);

        assertCompleteManifest(xmlParts);
    }

    private void assertCompleteManifest(XmlParts xmlParts) {
        assertTrue(xmlParts.containsUsesSdk());
        assertTrue(xmlParts.containsPermission());
        assertTrue(xmlParts.containsUsesPermission1());
        assertTrue(xmlParts.containsUsesPermission2());
        assertTrue(xmlParts.containsUsesPermission3());
        assertTrue(xmlParts.containsUsesPermission4());
        assertTrue(xmlParts.containsUsesPermission5());
        assertTrue(xmlParts.containsService());
        assertTrue(xmlParts.containsReceiver());
    }
}
