package net.jarlehansen.android.gcm.manifest.xml.android.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:14 AM
 */
@Namespace(prefix = "android")
public class Permission {

    @Attribute
    private String name = null;

    @Attribute
    private String protectionLevel = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setProtectionLevel(String protectionLevel) {
        this.protectionLevel = protectionLevel;
    }

    public String getName() {
        return name;
    }

    public String getProtectionLevel() {
        return protectionLevel;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                ", protectionLevel='" + protectionLevel + '\'' +
                '}';
    }
}
