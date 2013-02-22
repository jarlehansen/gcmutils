package net.jarlehansen.android.gcm.manifest.xml.android.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:20 AM
 */
@Namespace(prefix = "android")
public class UsesPermission {

    @Attribute
    private String name;

    public UsesPermission() {
    }

    public UsesPermission(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UsesPermission{" +
                "name='" + name + '\'' +
                '}';
    }
}
