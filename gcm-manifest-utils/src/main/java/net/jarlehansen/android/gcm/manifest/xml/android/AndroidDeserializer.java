package net.jarlehansen.android.gcm.manifest.xml.android;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 9:14 AM
 */
public interface AndroidDeserializer {
    public AndroidManifest deserialize();
}
