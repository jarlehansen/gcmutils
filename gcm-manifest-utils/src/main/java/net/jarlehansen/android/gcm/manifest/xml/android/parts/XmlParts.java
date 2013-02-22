package net.jarlehansen.android.gcm.manifest.xml.android.parts;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/22/13
 * Time: 8:47 AM
 */
public class XmlParts {
    private boolean usesSdk = false;
    private boolean permission = false;
    private boolean usesPermission1 = false;
    private boolean usesPermission2 = false;
    private boolean usesPermission3 = false;
    private boolean usesPermission4 = false;
    private boolean usesPermission5 = false;
    private boolean service = false;
    private boolean receiver = false;

    public XmlParts() {
    }

    public boolean containsUsesSdk() {
        return usesSdk;
    }

    public void setUsesSdk(boolean usesSdk) {
        this.usesSdk = usesSdk;
    }

    public boolean containsPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public boolean containsUsesPermission1() {
        return usesPermission1;
    }

    public void setUsesPermission1(boolean usesPermission1) {
        this.usesPermission1 = usesPermission1;
    }

    public boolean containsUsesPermission2() {
        return usesPermission2;
    }

    public void setUsesPermission2(boolean usesPermission2) {
        this.usesPermission2 = usesPermission2;
    }

    public boolean containsUsesPermission3() {
        return usesPermission3;
    }

    public void setUsesPermission3(boolean usesPermission3) {
        this.usesPermission3 = usesPermission3;
    }

    public boolean containsUsesPermission4() {
        return usesPermission4;
    }

    public void setUsesPermission4(boolean usesPermission4) {
        this.usesPermission4 = usesPermission4;
    }

    public boolean containsUsesPermission5() {
        return usesPermission5;
    }

    public void setUsesPermission5(boolean usesPermission5) {
        this.usesPermission5 = usesPermission5;
    }

    public boolean containsService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public boolean containsReceiver() {
        return receiver;
    }

    public void setReceiver(boolean receiver) {
        this.receiver = receiver;
    }
}
