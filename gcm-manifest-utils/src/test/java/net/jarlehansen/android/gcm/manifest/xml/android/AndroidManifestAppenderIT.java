package net.jarlehansen.android.gcm.manifest.xml.android;

import net.jarlehansen.android.gcm.manifest.xml.android.parts.XmlParts;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 9:24 AM
 */
public class AndroidManifestAppenderIT {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Test
    public void append() throws IOException {
        File file = tmpFolder.newFile();

        AndroidManifestAppender appender = new AndroidManifestAppender(new File("src/test/resources/AndroidManifest.xml"), file, "net.jarlehansen.android.gcm");
        appender.append(new XmlParts());

        assertTrue(file.isFile());
    }
}
