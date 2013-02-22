package net.jarlehansen.android.gcm.manifest.xml.android.model;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:36 AM
 */
@Root(strict = false)
@Namespace(prefix = "android")
public class Receiver {

    @Attribute
    private String name = null;

    @Attribute
    private String permission = null;

    @Element(name = "intent-filter", required = false)
    private IntentFilter intentFilter = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public IntentFilter getIntentFilter() {
        return intentFilter;
    }

    public void setIntentFilter(IntentFilter intentFilter) {
        this.intentFilter = intentFilter;
    }

    @Override
    public String toString() {
        return "Receiver{" +
                "name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", intentFilter=" + intentFilter +
                '}';
    }
}
