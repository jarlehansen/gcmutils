package net.jarlehansen.android.gcm.manifest;

import net.jarlehansen.android.gcm.manifest.xml.android.AndroidManifestDeserializer;
import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.parts.XmlParts;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/22/13
 * Time: 12:19 PM
 */
public class GCMUtilsManifestIT {
    private static final Logger logger = LoggerFactory.getLogger(GCMUtilsManifestIT.class);

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File inputFile;
    private File backupFile;

    @Before
    public void setUp() throws IOException {
        inputFile = folder.newFile();
    }

    @Test
    public void main_noGCMManifest() throws IOException {
        File manifestFile = new File("src/test/resources/NoGCM-AndroidManifest.xml");
        createBackupFile(manifestFile);

        GCMUtilsManifest.start(inputFile.getAbsolutePath(), "net.jarlehansen.android.test", false);

        assertTrue(backupFile.isFile());
        assertCompleteManifest(inputFile);
    }

    @Test
    public void main_partialManifest() throws IOException {
        File manifestFile = new File("src/test/resources/Partial-AndroidManifest.xml");
        createBackupFile(manifestFile);

        GCMUtilsManifest.start(inputFile.getAbsolutePath(), "net.jarlehansen.android.test", false);

        assertTrue(backupFile.isFile());
        assertCompleteManifest(inputFile);
    }

    @Test
    public void main_completeManifest() throws IOException {
        File manifestFile = new File("src/test/resources/AndroidManifest.xml");
        createBackupFile(manifestFile);

        GCMUtilsManifest.start(inputFile.getAbsolutePath(), "net.jarlehansen.android.test", false);

        assertCompleteManifest(inputFile);
    }

    @Test
    public void main_noGCMManifest_skipBackup() throws IOException {
        File manifestFile = new File("src/test/resources/NoGCM-AndroidManifest.xml");
        createBackupFile(manifestFile);

        System.setProperty("skipBackup", "true");

        GCMUtilsManifest.start(inputFile.getAbsolutePath(), "net.jarlehansen.android.test", true);

        assertFalse(backupFile.isFile());
        assertCompleteManifest(inputFile);
    }


    private void createBackupFile(File manifestFile) throws IOException {
        FileUtils.copyFile(manifestFile, inputFile);
        backupFile = GCMUtilsManifest.getBackupFile(inputFile);
        logger.info("Backup file: {}", backupFile.getAbsolutePath());
    }

    private void assertCompleteManifest(File file) {
        assertTrue(inputFile.isFile());

        AndroidManifest manifest = new AndroidManifestDeserializer(file).deserialize();
        XmlParts xmlParts = new AndroidManifestService("", "", "").populateXmlParts(manifest);

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
