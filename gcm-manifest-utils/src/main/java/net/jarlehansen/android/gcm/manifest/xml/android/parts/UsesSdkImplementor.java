package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.UsesSdk;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 9:17 AM
 */
public class UsesSdkImplementor implements XmlImplementor {

    public void populateXmlParts(XmlParts xmlParts, AndroidManifest androidManifest) {
        UsesSdk usesSdk = androidManifest.getUsesSdk();
        if (usesSdk == null)
            usesSdk = new UsesSdk();

        String minSdkVersion = usesSdk.getMinSdkVersion();
        xmlParts.setUsesSdk((minSdkVersion != null) && !"".equals(minSdkVersion));
    }
}
