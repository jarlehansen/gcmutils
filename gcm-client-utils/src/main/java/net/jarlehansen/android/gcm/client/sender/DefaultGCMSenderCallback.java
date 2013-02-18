package net.jarlehansen.android.gcm.client.sender;

import net.jarlehansen.android.gcm.client.log.GCMUtilsLog;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 10:37 AM
 */
public class DefaultGCMSenderCallback implements GCMSenderCallback {
    @Override
    public void onRequestSent(GCMSenderResponse response) {
        GCMUtilsLog.i("Status from regIdSender: ", response.toString());
    }
}
