package com.daiv.android.twitter.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.daiv.android.twitter.R;
import com.daiv.android.twitter.settings.AppSettings;
import com.daiv.android.twitter.ui.compose.ComposeActivity;

public class MaterialMainActivity extends AppCompatActivity {
    private static final String TAG = MaterialMainActivity.class.getSimpleName();

    public static final String NEW_TWITTER_MSG = "newMsgTwitter";

    public static boolean canSwitch = true;

    public static AppSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_main_activity);

        settings = AppSettings.getInstance(getApplicationContext());

        if (!settings.isTwitterLoggedIn) {
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compose = new Intent(getApplicationContext(), ComposeActivity.class);
                startActivity(compose);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(NEW_TWITTER_MSG));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Got message");

            Snackbar.make(findViewById(R.id.headlines_fragment), "You have new Twitts, lets refresh!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    };

    @Override
    protected void onPause() {

        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

        super.onPause();
    }
}
