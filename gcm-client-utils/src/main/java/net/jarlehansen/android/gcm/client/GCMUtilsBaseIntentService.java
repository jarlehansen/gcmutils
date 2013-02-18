package net.jarlehansen.android.gcm.client;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.properties.GCMUtilsProperties;

/**
 * Provides a few simple helper methods. The {@link #onMessage(android.content.Context, String)} simply parses String messages sent with the {@code GCMUtilsConstants.DATA_KEY_MSG}.
 * <p/>
 * {@link #onRegistered(android.content.Context, String)} and {@link #onUnregistered(android.content.Context, String)} will automatically send requests to the receiving server (read {@code receiver-url} from gcmutils.properties).
 * If this behavior is unwanted, simply override the methods.
 * <p/>
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 12:09 PM
 */
public abstract class GCMUtilsBaseIntentService extends GCMBaseIntentService {

    protected GCMUtilsBaseIntentService() {
    }

    protected GCMUtilsBaseIntentService(String... senderIds) {
        super(senderIds);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(GCMUtilsConstants.TAG, "onMessage");
        if (intent.hasExtra(GCMUtilsConstants.DATA_KEY_MSG)) {
            String msg = intent.getStringExtra(GCMUtilsConstants.DATA_KEY_MSG);
            Log.i(GCMUtilsConstants.TAG, "msg received: " + msg);
            onMessage(context, msg);
        }
    }

    protected void onMessage(Context context, String msg) {
    }

    @Override
    protected void onRegistered(Context context, String regId) {
        Log.i(GCMUtilsConstants.TAG, "onRegistered, regId=" + regId);
        GCMUtils.createRegSender(GCMUtilsProperties.GCMUTILS.getReceiverUrl(), regId).send();
    }

    @Override
    protected void onUnregistered(Context context, String regId) {
        Log.i(GCMUtilsConstants.TAG, "onUnregistered, regId=" + regId);
        GCMUtils.createUnregSender(GCMUtilsProperties.GCMUTILS.getReceiverUrl(), regId).send();
    }
}
