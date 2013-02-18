package net.jarlehansen.android.gcm.client;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gcm.GCMRegistrar;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.properties.GCMUtilsProperties;

public class GCMUtilsTestActivity extends Activity {
    static final String DISPLAY_INFO_ACTION = "net.jarlehansen.android.gcm.client.DISPLAY_INFO_ACTION";
    static final String DATA_KEY_REG_ID = "regId";

    static final String SERVER_URL = "http://10.0.2.2:10246";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(GCMUtilsConstants.TAG, "Started: " + this.getClass().getSimpleName());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showUnregisterButton();

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra(DATA_KEY_REG_ID))
                    showRegId(intent.getStringExtra(DATA_KEY_REG_ID));
                else if (intent.hasExtra(GCMUtilsConstants.DATA_KEY_MSG))
                    showMsg(intent.getStringExtra(GCMUtilsConstants.DATA_KEY_MSG));
            }
        }, new IntentFilter(DISPLAY_INFO_ACTION));

        GCMRegistrar.checkDevice(this);
        GCMUtils.checkExtended(this);
        String regId = GCMUtils.getAndSendRegistrationId(this);
        showRegId(regId);
    }

    private void showUnregisterButton() {
        Button regButton = (Button) findViewById(R.id.regButton);
        regButton.setText("Unregister");
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unregister();
                showRegisterButton();

                TextView regIdView = (TextView) findViewById(R.id.regIdView);
                regIdView.setText("");
            }
        });
    }

    private void showRegisterButton() {
        Button regButton = (Button) findViewById(R.id.regButton);
        regButton.setText("Register");
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
                showUnregisterButton();
            }
        });
    }

    private void unregister() {
        GCMRegistrar.unregister(this);
    }

    private void register() {
        GCMRegistrar.register(this, GCMUtilsProperties.GCMUTILS.getSenderId());
    }

    private void showRegId(String regId) {
        TextView regIdTextView = (TextView) findViewById(R.id.regIdView);
        regIdTextView.setText(regId);
    }

    private void showMsg(String msg) {
        TextView msgTextView = (TextView) findViewById(R.id.msgTextView);
        msgTextView.setText(msg);
    }
}

