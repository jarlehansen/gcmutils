package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 9:18 AM
 */
public interface XmlImplementor {
    public void populateXmlParts(XmlParts xmlParts, AndroidManifest androidManifest);
}
