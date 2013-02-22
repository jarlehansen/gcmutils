package net.jarlehansen.android.gcm.manifest.xml.android.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:57 AM
 */
@Namespace(prefix = "android")
public class Category {

    @Attribute
    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
