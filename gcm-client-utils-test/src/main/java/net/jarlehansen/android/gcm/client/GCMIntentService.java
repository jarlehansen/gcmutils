package net.jarlehansen.android.gcm.client;

import android.content.Context;
import android.content.Intent;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.log.GCMUtilsLog;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 1:54 PM
 */
public class GCMIntentService extends GCMUtilsBaseIntentService {

    @Override
    protected void onMessage(Context context, Intent intent) {
        String msg = intent.getStringExtra(GCMUtilsConstants.DATA_KEY_MSG);
        GCMUtilsLog.i("onMessage: ", msg);

        Intent appIntent = new Intent(GCMUtilsTestActivity.DISPLAY_INFO_ACTION);
        appIntent.putExtra(GCMUtilsConstants.DATA_KEY_MSG, msg);
        context.sendBroadcast(appIntent);
    }

    @Override
    protected void onError(Context context, String msg) {
        GCMUtilsLog.e("onError: ", msg);
    }

    @Override
    protected void onRegistered(Context context, String regId) {
        super.onRegistered(context, regId);
        Intent intent = new Intent(GCMUtilsTestActivity.DISPLAY_INFO_ACTION);
        intent.putExtra(GCMUtilsTestActivity.DATA_KEY_REG_ID, regId);
        context.sendBroadcast(intent);
    }
}
