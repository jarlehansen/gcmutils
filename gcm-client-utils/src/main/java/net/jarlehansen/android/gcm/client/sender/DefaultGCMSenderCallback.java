package net.jarlehansen.android.gcm.client.sender;

import android.util.Log;
import net.jarlehansen.android.gcm.GCMUtilsConstants;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 10:37 AM
 */
public class DefaultGCMSenderCallback implements GCMSenderCallback {
    @Override
    public void onRequestSent(GCMSenderResponse response) {
        Log.i(GCMUtilsConstants.TAG, "Status from regIdSender: " + response);
    }
}
