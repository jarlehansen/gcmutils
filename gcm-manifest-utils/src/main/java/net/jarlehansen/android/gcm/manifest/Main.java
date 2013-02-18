package net.jarlehansen.android.gcm.manifest;

import java.io.File;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 2:02 PM
 */
public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            String manifestPath = args[0];
            File manifestFile = new File(manifestPath);

            if (manifestFile.isFile()) {

            } else {
                throw new IllegalArgumentException("The path '" + manifestPath + "' is not pointing to a valid file");
            }
        } else {
            throw new IllegalArgumentException("main() must be started with 1 argument (path to manifest-file)");
        }
    }
}
