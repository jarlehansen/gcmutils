package net.jarlehansen.android.gcm.manifest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 2:02 PM
 */
public enum GCMUtilsManifest {
    ;

    private static final Logger logger = LoggerFactory.getLogger(GCMUtilsManifest.class);

    public static void start(String manifestPath, String packageName, boolean skipBackup) {
        File manifestFile = new File(manifestPath);
        if (manifestFile.isFile()) {

            String inputPath;
            if (skipBackup) {
                logger.info("skipBackup is enabled, no backup is created");
                inputPath = manifestPath;
            } else {
                inputPath = createBackup(manifestFile);
            }

            logger.info("Input file: {}", inputPath);
            logger.info("Output file: {}", manifestPath);
            logger.info("Package name: {}", packageName);

            new AndroidManifestService(inputPath, manifestPath, packageName).execute();
            logger.info("AndroidManifest updated successfully");
        } else {
            throw new IllegalArgumentException("The path '" + manifestPath + "' is not pointing to a valid file");
        }
    }

    private static String createBackup(File manifestFile) {
        File backup = getBackupFile(manifestFile);
        try {
            FileUtils.copyFile(manifestFile, backup);
            logger.info("Created backup file: {}", backup.getAbsolutePath());
            return backup.getAbsolutePath();
        } catch (IOException io) {
            throw new IllegalStateException("Unable to create backup file: " + backup.getAbsolutePath(), io);
        }
    }

    static File getBackupFile(File manifestFile) {
        String manifestPath = manifestFile.getAbsolutePath();
        String path = FilenameUtils.getFullPath(manifestPath);
        String name = FilenameUtils.getBaseName(manifestPath);
        String extension = FilenameUtils.getExtension(manifestPath);

        String backup = path + name + "-backup." + extension;
        return new File(backup);
    }
}
