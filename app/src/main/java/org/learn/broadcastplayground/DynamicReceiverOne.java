package org.learn.broadcastplayground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DynamicReceiverOne extends BroadcastReceiver {
    private static final String TAG = "DynamicReceiverOne";
    private final static String EXTRAS = "PATH";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle resultExtras = getResultExtras(true);
        StringBuilder stringBuilder = new StringBuilder();
        String intentActionString = intent.getAction();

        if (intentActionString.equals("org.learn.broadcastplayground.ORDERED_STUFF")) {
            resultExtras.putString(EXTRAS, resultExtras.get(EXTRAS) + " -> " + TAG);
        } else {
            stringBuilder.append("Action: " + intent.getAction() + "\n");
            stringBuilder.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
            String log = stringBuilder.toString();
            Toast.makeText(context, log, Toast.LENGTH_LONG).show();
        }
    }
}
