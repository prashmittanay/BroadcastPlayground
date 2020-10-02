package org.learn.broadcastplayground;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private  DynamicReceiverOne mDynamicReceiverOne;
    private DynamicReceiverTwo mDynamicReceiverTwo;
    private final static String EXTRAS = "PATH";
    private Button mNormalBroadcastButton;
    private Button mOrderedBroadcastButton;
    private Button mStickyInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDynamicReceiverOne = new DynamicReceiverOne();
        mDynamicReceiverTwo = new DynamicReceiverTwo();
        mNormalBroadcastButton = findViewById(R.id.button_normal_broadcast);
        mOrderedBroadcastButton = findViewById(R.id.button_ordered_broadcast);
        mStickyInfoButton = findViewById(R.id.button_sticky_info);
        attachButtonListeners();
    }

    private void attachButtonListeners() {
        mNormalBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DynamicReceiverOne.class);
                intent.setAction("org.learn.broadcastplayground.CUSTOM_BROADCAST");
                sendBroadcast(intent);
            }
        });

        mOrderedBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constructOrderedBroadcast();
            }
        });

        mStickyInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent intent = getApplicationContext().registerReceiver(null, intentFilter);

                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;

                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

                Toast.makeText(MainActivity.this, "Is Charging: " + isCharging + "\n" +
                        "Charge Plug: " + chargePlug + "\n" +
                        "USB Charge: " + usbCharge + "\n" +
                        "AC Charge: " + acCharge + "\n", Toast.LENGTH_SHORT).show();
            }
        });
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
        this.unregisterReceiver(mDynamicReceiverTwo);
    }

    private void bindBroadcastReceivers() {
        IntentFilter intentFilterConnectivity = new IntentFilter();
        intentFilterConnectivity.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(mDynamicReceiverOne, intentFilterConnectivity);

        IntentFilter intentFilterOne = new IntentFilter("org.learn.broadcastplayground.ORDERED_STUFF");
        this.registerReceiver(mDynamicReceiverOne, intentFilterOne);

        IntentFilter intentFilterTwo = new IntentFilter("org.learn.broadcastplayground.ORDERED_STUFF");
        this.registerReceiver(mDynamicReceiverTwo, intentFilterTwo);
    }

    private void constructOrderedBroadcast() {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS, TAG);
        Intent intent = new Intent("org.learn.broadcastplayground.ORDERED_STUFF");
        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle resultExtras = getResultExtras(true);
                Toast.makeText(context, resultExtras.getString(EXTRAS), Toast.LENGTH_SHORT).show();
            }
        }, null, Activity.RESULT_OK, null, bundle);
    }
}