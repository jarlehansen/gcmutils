package net.jarlehansen.android.gcm.client;

import android.content.Context;
import com.google.android.gcm.GCMRegistrar;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.log.GCMUtilsLog;
import net.jarlehansen.android.gcm.client.properties.GCMUtilsProperties;
import net.jarlehansen.android.gcm.client.sender.DefaultGCMSenderCallback;
import net.jarlehansen.android.gcm.client.sender.GCMSender;
import net.jarlehansen.android.gcm.client.sender.GCMSenderCallback;
import net.jarlehansen.android.gcm.client.sender.GCMSenderImpl;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utilities for Google Cloud Messaging.
 * <p>Features:
 * <ul>
 * <li>Registration id handling (both for registration and unregistration), automatically send a request to a server containing the registration id. Supports features such as exponential backoff.
 * <li>Extended verification of project with {@link GCMUtils#checkExtended(android.content.Context)}. Verifies the GCM service created in the project.
 * <li>Uses a properties file ({@code gcmutils.properties}) to make it easy to provide configuration options, such as {@code receiver-url} and {@code sender-id}.
 * <li>An alternative base intent service ({@link GCMUtilsBaseIntentService}), that includes a few helper methods. Such as automatic registrationId handling.
 * </ul>
 * <p/>
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 9:00 AM
 */
public enum GCMUtils {
    ;

    private static GCMSender createSender(String param, String receiverUrl, String regId) {
        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(param, regId));

        return new GCMSenderImpl(receiverUrl, nameValuePairs);
    }

    private static String getProperty(String key, Context context) {
        boolean initialized = GCMUtilsProperties.GCMUTILS.init(context);
        if (initialized) {
            return GCMUtilsProperties.GCMUTILS.get(key);
        } else
            throw new IllegalArgumentException("Unable to load properties file '" + GCMUtilsProperties.GCMUTILS.getFileName() + "'");
    }

    private static String getReceiverUrl(Context context) {
        return getProperty(GCMUtilsConstants.PROPS_KEY_RECEIVERURL, context);
    }

    private static String getSenderId(Context context) {
        return getProperty(GCMUtilsConstants.PROPS_KEY_SENDERID, context);
    }

    private static void populateOptionalProperties(GCMSender sender, Context context) {
        String backoffMillis = getProperty(GCMUtilsConstants.PROPS_KEY_BACKOFF, context);
        String numOfRetries = getProperty(GCMUtilsConstants.PROPS_KEY_RETRIES, context);

        if (backoffMillis.length() > 0)
            sender.setBackoffMillis(Integer.parseInt(backoffMillis));

        if (numOfRetries.length() > 0)
            sender.setRetries(Integer.parseInt(numOfRetries));
    }

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
        String receiverUrl = getReceiverUrl(context);

        GCMSender sender = createSender(receiverUrl, valuePairs);
        populateOptionalProperties(sender, context);

        return sender;
    }

    /**
     * Create registrationId sender. Uses {@link GCMUtilsConstants#PARAM_KEY_REGID}.
     */
    public static GCMSender createRegSender(String receiverUrl, String regId) {
        return GCMUtils.createSender(GCMUtilsConstants.PARAM_KEY_REGID, receiverUrl, regId);
    }

    /**
     * Reads the {@code receiver-url(required)}, {@code request-backoff-millis(optional)} and {@code request-retries(optional)} properties from {@code gcmutils.properties}.
     *
     * @see #createRegSender(String, String)
     */
    public static GCMSender createRegSender(Context context, String regId) {
        String receiverUrl = getReceiverUrl(context);
        GCMSender sender = GCMUtils.createRegSender(receiverUrl, regId);
        populateOptionalProperties(sender, context);

        return sender;
    }

    /**
     * Create sender for unregistration. Uses {@link GCMUtilsConstants#PARAM_KEY_UNREGID}.
     */
    public static GCMSender createUnregSender(String receiverUrl, String regId) {
        return GCMUtils.createSender(GCMUtilsConstants.PARAM_KEY_UNREGID, receiverUrl, regId);
    }

    /**
     * Reads the {@code receiver-url(required)}, {@code request-backoff-millis(optional)} and {@code request-retries(optional)} properties from {@code gcmutils.properties}.
     *
     * @see #createUnregSender(String, String)
     */
    public static GCMSender createUnregSender(Context context, String regId) {
        String receiverUrl = getReceiverUrl(context);
        GCMSender sender = GCMUtils.createUnregSender(receiverUrl, regId);
        populateOptionalProperties(sender, context);

        return sender;
    }

    /**
     * Checks if registrationId is available, if it is the regId is sent automatically using the {@link #createRegSender(String, String)}.
     * If no registrationId is retrieved, a registration event is started using {@code GCMRegistrar.register(android.content.Context, String...)}.
     * <p/>
     * Reads the {@code receiver-url(required)}, {@code request-backoff-millis(optional)} and {@code request-retries(optional)} properties from {@code gcmutils.properties}.
     */
    public static String getAndSendRegistrationId(Context context) {
        return getAndSendRegistrationId(context, new DefaultGCMSenderCallback());
    }

    /**
     * Add a callback class to be notified after the GCM registration request has been sent.
     *
     * @see #getAndSendRegistrationId(android.content.Context)
     */
    public static String getAndSendRegistrationId(Context context, GCMSenderCallback callback) {
        String receiverUrl = getReceiverUrl(context);
        String senderId = getSenderId(context);

        String regId = GCMRegistrar.getRegistrationId(context);
        if (regId == null || "".equals(regId)) {
            GCMUtilsLog.i("No previous registration id, starting registration process");
            GCMRegistrar.register(context, senderId);
        } else {
            GCMUtilsLog.i("Retrieving previous registration id");
            GCMSender sender = GCMUtils.createRegSender(receiverUrl, regId);
            sender.setCallback(callback);

            populateOptionalProperties(sender, context);
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
        GCMUtilsVerifier.checkExtended(context);
    }
}
