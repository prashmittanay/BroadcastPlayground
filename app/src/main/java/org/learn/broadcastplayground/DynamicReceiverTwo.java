package org.learn.broadcastplayground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DynamicReceiverTwo extends BroadcastReceiver {
    private static final String TAG = "DynamicReceiverTwo";
    private final static String EXTRAS = "PATH";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle resultExtras = getResultExtras(true);
        resultExtras.putString(EXTRAS, resultExtras.get(EXTRAS) + " -> " + TAG);
    }
}
