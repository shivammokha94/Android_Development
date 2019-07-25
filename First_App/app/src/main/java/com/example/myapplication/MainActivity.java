package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.view.View;


public class MainActivity extends Activity {
     String msg = "Android : ";

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         Log.d(msg, "This is an on create event");
     }

     public void startService(View view){
         startService(new Intent(getBaseContext(), myservice.class));
     }

     public void stopService(View view){
         stopService(new Intent(getBaseContext(), myservice.class));
    }
}


