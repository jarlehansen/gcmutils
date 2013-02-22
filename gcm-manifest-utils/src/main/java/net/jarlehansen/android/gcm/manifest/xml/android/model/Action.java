package net.jarlehansen.android.gcm.manifest.xml.android.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:39 AM
 */
@Namespace(prefix = "android")
public class Action {

    @Attribute
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Action{" +
                "name='" + name + '\'' +
                '}';
    }
}
