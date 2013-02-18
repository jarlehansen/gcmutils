package net.jarlehansen.android.gcm.client.sender;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 10:36 AM
 */
public interface GCMSenderCallback {
    public void onRequestSent(GCMSenderResponse response);
}
