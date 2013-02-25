package net.jarlehansen.android.gcm.manifest;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 2:02 PM
 */
public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            String manifestPath = args[0];
            String packageName = args[1];
            String skipBackup = System.getProperty("skipBackup");

            GCMUtilsManifest.start(manifestPath, packageName, Boolean.valueOf(skipBackup));
        } else {
            throw new IllegalArgumentException("gcm-manifest-utils must be started with 2 (path to manifest-file and package name) arguments\n" +
                    "For example: java -jar gcm-manifest-utils.jar my-project/AndroidManifest.xml net.jarlehansen.android.gcm");
        }
    }
}
