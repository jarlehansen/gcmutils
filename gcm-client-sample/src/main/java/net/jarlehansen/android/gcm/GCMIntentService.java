package net.jarlehansen.android.gcm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import net.jarlehansen.android.gcm.client.GCMUtilsBaseIntentService;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 1:12 PM
 */
public class GCMIntentService extends GCMUtilsBaseIntentService {

    @Override
    protected void onMessage(Context context, String msg) {
        Log.i(TAG, "Message received: " + msg);
        Intent intent = new Intent(SimpleGCMUtilsActivity.MSG_ACTION);
        intent.putExtra(GCMUtilsConstants.DATA_KEY_MSG, msg);
        context.sendBroadcast(intent);
    }

    @Override
    protected void onError(Context context, String error) {
        Log.e(GCMUtilsConstants.TAG, "onError: " + error);
    }
}
