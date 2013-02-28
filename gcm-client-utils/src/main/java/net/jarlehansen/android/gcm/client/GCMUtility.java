package net.jarlehansen.android.gcm.client;

import android.content.Context;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.account.GCMAccountManagement;
import net.jarlehansen.android.gcm.client.account.GCMAccountManagementImpl;
import net.jarlehansen.android.gcm.client.properties.GCMUtilsProperties;
import net.jarlehansen.android.gcm.client.sender.GCMSender;
import net.jarlehansen.android.gcm.client.sender.GCMSenderImpl;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/27/13
 * Time: 1:07 PM
 */
enum GCMUtility {
    ;

    private static volatile BasicNameValuePair mainEmailAccount = null;

    static BasicNameValuePair getMainAccount(Context context) {
        if (mainEmailAccount == null) {
            GCMAccountManagement accountManagement = new GCMAccountManagementImpl();
            String mainAccount = accountManagement.getMainAccount(context);
            if (!"".equals(mainAccount))
                mainEmailAccount = new BasicNameValuePair(GCMUtilsConstants.PARAM_KEY_EMAIL, mainAccount);
        }

        return mainEmailAccount;
    }

    static GCMSender createSender(String param, String receiverUrl, String regId) {
        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(param, regId));

        return new GCMSenderImpl(receiverUrl, nameValuePairs);
    }

    static String getReceiverUrl(Context context) {
        return getProperty(GCMUtilsConstants.PROPS_KEY_RECEIVERURL, context);
    }

    static String getSenderId(Context context) {
        return getProperty(GCMUtilsConstants.PROPS_KEY_SENDERID, context);
    }

    static void populateOptionalProperties(GCMSender sender, Context context) {
        String backoffMillis = getProperty(GCMUtilsConstants.PROPS_KEY_BACKOFF, context);
        String numOfRetries = getProperty(GCMUtilsConstants.PROPS_KEY_RETRIES, context);

        if (backoffMillis.length() > 0)
            sender.setBackoffMillis(Integer.parseInt(backoffMillis));

        if (numOfRetries.length() > 0)
            sender.setRetries(Integer.parseInt(numOfRetries));
    }

    static String getProperty(String key, Context context) {
        boolean initialized = GCMUtilsProperties.GCMUTILS.init(context);
        if (initialized) {
            return GCMUtilsProperties.GCMUTILS.get(key);
        } else
            throw new IllegalArgumentException("Unable to load properties file '" + GCMUtilsProperties.GCMUTILS.getFileName() + "'");
    }
}
