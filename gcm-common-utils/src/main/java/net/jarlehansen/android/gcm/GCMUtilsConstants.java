package net.jarlehansen.android.gcm;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 9:09 AM
 */
public enum GCMUtilsConstants {
    ;

    public static final String TAG = "GCMUtils";

    public static final String PARAM_KEY_REGID = "regId";
    public static final String PARAM_KEY_UNREGID = "unregId";

    public static final String DATA_KEY_MSG = "msg";

    public static final String PROPS_KEY_RECEIVERURL = "receiver-url";
    public static final String PROPS_KEY_SENDERID = "sender-id";
    public static final String PROPS_KEY_BACKOFF = "request-backoff-millis";
    public static final String PROPS_KEY_RETRIES = "request-retries";
    public static final String PROPS_KEY_CHECKEXTENDED = "check-extended";
}
