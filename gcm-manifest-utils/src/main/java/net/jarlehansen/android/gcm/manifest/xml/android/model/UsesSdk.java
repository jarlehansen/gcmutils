package net.jarlehansen.android.gcm.manifest.xml.android.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:06 AM
 */
@Root(strict = false)
@Namespace(prefix = "android")
public class UsesSdk {

    @Attribute
    private String minSdkVersion = null;

    public void setMinSdkVersion(String minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public String getMinSdkVersion() {
        return minSdkVersion;
    }


    @Override
    public String toString() {
        return "UsesSdk{" +
                "minSdkVersion='" + minSdkVersion + '\'' +
                '}';
    }
}
