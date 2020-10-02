package org.learn.broadcastplayground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private  DynamicReceiverOne mDynamicReceiverOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDynamicReceiverOne = new DynamicReceiverOne();
    }

    @Override
    protected void onResume() {
        super.onResume();

        bindBroadcastReceivers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mDynamicReceiverOne);
    }

    private void bindBroadcastReceivers() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(mDynamicReceiverOne, intentFilter);
    }
}