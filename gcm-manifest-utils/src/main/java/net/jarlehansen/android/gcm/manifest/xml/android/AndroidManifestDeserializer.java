package net.jarlehansen.android.gcm.manifest.xml.android;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 9:05 AM
 */
public class AndroidManifestDeserializer implements AndroidDeserializer {
    private final File file;

    public AndroidManifestDeserializer(File file) {
        this.file = file;
    }

    public AndroidManifest deserialize() {
        Serializer serializer = new Persister();
        try {
            return serializer.read(AndroidManifest.class, file);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to read file: " + file.getAbsolutePath(), e);
        }
    }
}
