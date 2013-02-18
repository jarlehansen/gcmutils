package net.jarlehansen.android.gcm.client.sender;

import android.content.Context;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 9:01 AM
 */
public interface GCMSender {
    public void send();

    public GCMSender setCallback(GCMSenderCallback callback);

    public GCMSender setBackoffMillis(int millis);

    public GCMSender setRetries(int retries);

    public GCMSender enableResultToast(Context context);
}
