package net.jarlehansen.android.gcm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gcm.GCMRegistrar;
import net.jarlehansen.android.gcm.client.GCMUtils;

public class SimpleGCMUtilsActivity extends Activity {
    static final String MSG_ACTION = "net.jarlehansen.android.gcm.MSG_ACTION";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            Log.i(GCMUtilsConstants.TAG, "name:" + account.name + " type:" + account.type);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra(GCMUtilsConstants.DATA_KEY_MSG);
                TextView msgTextView = (TextView) findViewById(R.id.msgTextView);
                msgTextView.setText(msg);
            }
        }, new IntentFilter(MSG_ACTION));

        GCMRegistrar.checkDevice(this);
        GCMUtils.checkExtended(this);
        GCMUtils.getAndSendRegIdAndEmail(this);
    }
}
