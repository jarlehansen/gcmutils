package net.jarlehansen.android.gcm;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/25/13
 * Time: 9:12 AM
 */
public class FileResetTest {

    @Test
    public void setupTestFile() throws IOException {
        File srcFile = new File("src/test/resources/AndroidManifest.xml");
        File destFile = new File("target/AndroidManifest.xml");
        FileUtils.copyFile(srcFile, destFile);
    }
}
