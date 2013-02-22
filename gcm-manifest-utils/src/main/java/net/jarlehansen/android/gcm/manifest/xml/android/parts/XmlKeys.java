package net.jarlehansen.android.gcm.manifest.xml.android.parts;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/22/13
 * Time: 7:58 AM
 */
public enum XmlKeys {
    ;

    public static final String USES_SDK = "8";

    public static final String PERMISSION = ".permission.C2D_MESSAGE";

    public static final String USES_PERMISSION_1 = PERMISSION;
    public static final String USES_PERMISSION_2 = "com.google.android.c2dm.permission.RECEIVE";
    public static final String USES_PERMISSION_3 = "android.permission.INTERNET";
    public static final String USES_PERMISSION_4 = "android.permission.GET_ACCOUNTS";
    public static final String USES_PERMISSION_5 = "android.permission.WAKE_LOCK";

    public static final String SERVICE = ".GCMIntentService";

    public static final String RECEIVER = "com.google.android.gcm.GCMBroadcastReceiver";
    public static final String RECEIVER_PERMISSION = "com.google.android.c2dm.permission.SEND";

    public static final String RECEIVER_ACTION1 = "com.google.android.c2dm.intent.RECEIVE";
    public static final String RECEIVER_ACTION2 = "com.google.android.c2dm.intent.REGISTRATION";

}
