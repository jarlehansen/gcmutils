package net.jarlehansen.android.gcm.client;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.google.android.gcm.GCMRegistrar;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.log.GCMUtilsLog;
import net.jarlehansen.android.gcm.client.sender.DefaultGCMSenderCallback;
import net.jarlehansen.android.gcm.client.sender.GCMSender;
import net.jarlehansen.android.gcm.client.sender.GCMSenderCallback;
import net.jarlehansen.android.gcm.client.sender.GCMSenderImpl;
import org.apache.http.message.BasicNameValuePair;

import java.util.Arrays;

/**
 * Utilities for Google Cloud Messaging.
 * <p>Features:
 * <ul>
 * <li>Registration id handling (both for registration and unregistration), that will automatically send a request to a server containing the registration id. Supports features such as exponential backoff.
 * <li>Extended verification of project with {@link GCMUtils#checkExtended(android.content.Context)}. Verifies the GCM service created in the project.
 * <li>Uses the file {@code gcmutils.properties} to make it easy to provide configuration options, such as {@code receiver-url} and {@code sender-id}.
 * <li>An alternative base intent service ({@link GCMUtilsBaseIntentService}), that includes a few helper methods, such as automatic registrationId handling.
 * </ul>
 * <p/>
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 9:00 AM
 */
public enum GCMUtils {
    ;

    /**
     * Create a sender with custom name-value-pairs.
     */
    public static GCMSender createSender(String receiverUrl, BasicNameValuePair... valuePairs) {
        return new GCMSenderImpl(receiverUrl, Arrays.asList(valuePairs));
    }

    /**
     * Reads the {@code receiver-url(required)}, {@code request-backoff-millis(optional)} and {@code request-retries(optional)} properties from {@code gcmutils.properties}.
     *
     * @see #createSender(String, org.apache.http.message.BasicNameValuePair...)
     */
    public static GCMSender createSender(Context context, BasicNameValuePair... valuePairs) {
        String receiverUrl = GCMUtility.getReceiverUrl(context);
        GCMSender sender = createSender(receiverUrl, valuePairs);
        GCMUtility.populateOptionalProperties(sender, context);

        return sender;
    }

    /**
     * Create registrationId sender. Uses {@link GCMUtilsConstants#PARAM_KEY_REGID}.
     */
    public static GCMSender createRegSender(String receiverUrl, String regId) {
        return GCMUtility.createSender(GCMUtilsConstants.PARAM_KEY_REGID, receiverUrl, regId);
    }

    /**
     * Reads the {@code receiver-url(required)}, {@code request-backoff-millis(optional)} and {@code request-retries(optional)} properties from {@code gcmutils.properties}.
     *
     * @see #createRegSender(String, String)
     */
    public static GCMSender createRegSender(Context context, String regId) {
        String receiverUrl = GCMUtility.getReceiverUrl(context);
        GCMSender sender = GCMUtils.createRegSender(receiverUrl, regId);
        GCMUtility.populateOptionalProperties(sender, context);
        return sender;
    }

    /**
     * Sends both registration id and email address. If no email address is registered on the device, the email parameter is not sent.
     * If multiple accounts are registered, the first one is selected.
     *
     * @see #createRegSender(android.content.Context, String)
     */
    public static GCMSender createRegAndEmailSender(Context context, String regId) {
        BasicNameValuePair mainAccount = GCMUtility.getMainAccount(context);
        if (mainAccount == null) {
            return GCMUtils.createRegSender(context, regId);
        } else {
            BasicNameValuePair valuePairRegId = new BasicNameValuePair(GCMUtilsConstants.PARAM_KEY_REGID, regId);
            GCMSender sender = GCMUtils.createSender(context, valuePairRegId, mainAccount);
            GCMUtility.populateOptionalProperties(sender, context);
            return sender;
        }
    }

    /**
     * Create sender for unregistration. Uses {@link GCMUtilsConstants#PARAM_KEY_UNREGID}.
     */
    public static GCMSender createUnregSender(String receiverUrl, String regId) {
        return GCMUtility.createSender(GCMUtilsConstants.PARAM_KEY_UNREGID, receiverUrl, regId);
    }

    /**
     * Reads the {@code receiver-url(required)}, {@code request-backoff-millis(optional)} and {@code request-retries(optional)} properties from {@code gcmutils.properties}.
     *
     * @see #createUnregSender(String, String)
     */
    public static GCMSender createUnregSender(Context context, String regId) {
        String receiverUrl = GCMUtility.getReceiverUrl(context);
        GCMSender sender = GCMUtils.createUnregSender(receiverUrl, regId);
        GCMUtility.populateOptionalProperties(sender, context);
        return sender;
    }

    /**
     * Sends both registration id and email address. If no email address is registered on the device, the email parameter is not sent.
     * If multiple accounts are registered, the first one is selected.
     *
     * @see #createUnregSender(android.content.Context, String)
     */
    public static GCMSender createUnregAndEmailSender(Context context, String regId) {
        BasicNameValuePair mainAccount = GCMUtility.getMainAccount(context);
        if (mainAccount == null) {
            return GCMUtils.createUnregSender(context, regId);
        } else {
            BasicNameValuePair valuePairUnregId = new BasicNameValuePair(GCMUtilsConstants.PARAM_KEY_UNREGID, regId);
            GCMSender sender = GCMUtils.createSender(context, valuePairUnregId, mainAccount);
            GCMUtility.populateOptionalProperties(sender, context);
            return sender;
        }
    }

    /**
     * Checks if registrationId is available, if it is the regId is sent automatically using the {@link #createRegSender(String, String)}.
     * If no registrationId is retrieved, a registration event is started using {@code GCMRegistrar.register(android.content.Context, String...)}.
     * <p/>
     * Reads the {@code receiver-url(required)}, {@code request-backoff-millis(optional)} and {@code request-retries(optional)} properties from {@code gcmutils.properties}.
     */
    public static String getAndSendRegId(Context context) {
        return getAndSendRegId(context, new DefaultGCMSenderCallback());
    }

    /**
     * Sends both registration id and email address. If no email address is registered on the device, the email parameter is not sent.
     * If multiple accounts are registered, the first one is selected.
     *
     * @see #getAndSendRegId(android.content.Context)
     */
    public static String getAndSendRegIdAndEmail(Context context) {
        return getAndSendRegIdAndEmail(context, new DefaultGCMSenderCallback());
    }

    /**
     * Add a callback class to be notified after the GCM registration request has been sent.
     *
     * @see #getAndSendRegId(android.content.Context)
     */
    public static String getAndSendRegId(Context context, GCMSenderCallback callback) {
        return GCMUtils.getAndSendRegParameters(context, callback, null);
    }

    /**
     * Add a callback class to be notified after the GCM registration request has been sent.
     *
     * @see #getAndSendRegIdAndEmail(android.content.Context)
     */
    public static String getAndSendRegIdAndEmail(Context context, GCMSenderCallback callback) {
        return getAndSendRegParameters(context, callback, GCMUtility.getMainAccount(context));
    }

    private static String getAndSendRegParameters(Context context, GCMSenderCallback callback, BasicNameValuePair additionalParam) {
        String regId = GCMRegistrar.getRegistrationId(context);
        String receiverUrl = GCMUtility.getReceiverUrl(context);
        String senderId = GCMUtility.getSenderId(context);

        BasicNameValuePair valuePairRegId = new BasicNameValuePair(GCMUtilsConstants.PARAM_KEY_REGID, regId);
        if (regId == null || "".equals(regId)) {
            GCMUtilsLog.i("No previous registration id, starting registration process");
            GCMRegistrar.register(context, senderId);
        } else {
            GCMUtilsLog.i("Retrieving previous registration id");
            final GCMSender sender;
            if (additionalParam == null)
                sender = GCMUtils.createSender(receiverUrl, valuePairRegId);
            else
                sender = GCMUtils.createSender(receiverUrl, valuePairRegId, additionalParam);

            sender.setCallback(callback);
            GCMUtility.populateOptionalProperties(sender, context);
            sender.send();
        }

        return regId;
    }

    /**
     * An extended verification of the project.
     * Calls {@code GCMRegistrar.checkManifest(android.content.Context)} and also adds a verification of the GCMIntentService class.
     * Should only be used during development and should not be used if you for some reason have a differently named service (than GCMIntentService).
     * <p/>
     * <strong>Example (during development):</strong>
     * <pre>
     * {@code
     * GCMRegistrar.checkDevice(this);
     * GCMUtils.checkExtended(this);
     * }
     * </pre>
     */
    public static void checkExtended(Context context) {
        String checkExtended = GCMUtility.getProperty(GCMUtilsConstants.PROPS_KEY_CHECKEXTENDED, context);

        if (isCheckExtendedEnabled(checkExtended, context.getApplicationInfo().flags, ApplicationInfo.FLAG_DEBUGGABLE))
            GCMUtilsVerifier.checkExtended(context);
        else
            GCMUtilsLog.i("Extended check is disabled");
    }

    // package-protected for testing
    static boolean isCheckExtendedEnabled(String checkExtended, int flags, int debugFlag) {
        boolean enabled = false;

        if ("enabled".equals(checkExtended))
            enabled = true;
        else if ("".equals(checkExtended)) {
            if ((flags &= debugFlag) != 0)
                enabled = true;
            else
                GCMUtilsLog.i("ApplicationInfo.FLAG_DEBUGGABLE is set to false");
        }

        return enabled;
    }
}
