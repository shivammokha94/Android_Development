package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;




public class myreceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("log", "this is shown");
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
    }
}