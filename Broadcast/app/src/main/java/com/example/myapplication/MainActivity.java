package com.example.myapplication;

import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;



public class MainActivity extends Activity {

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // broadcast a custom intent.

    public void broadcastIntent(View view){
        Intent intent = new Intent();
        intent.setAction("com.myapplication.CUSTOM_INTENT");
        sendBroadcast(intent);
    }
}