package net.jarlehansen.android.gcm.manifest;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 2:13 PM
 */
public class GCMUtilsMainfestTest {

    @Test(expected = IllegalArgumentException.class)
    public void main_invalidFile() {
        GCMUtilsManifest.start("invalid-file", "", false);
    }

    @Test
    public void getBackupPath() {
        File backupPath = GCMUtilsManifest.getBackupFile(new File("/folder1/folder2/my-file.txt"));
        assertEquals("/folder1/folder2/my-file-backup.txt", backupPath.getAbsolutePath());
    }

    @Test
    public void getBackupPath_windowsPath() {
        File backupPath = GCMUtilsManifest.getBackupFile(new File("c:\\folder1\\folder2\\my-file.txt"));
        assertTrue(backupPath.getPath().endsWith("c:\\folder1\\folder2\\my-file-backup.txt"));
    }
}
