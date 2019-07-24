package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;


public class MainActivity extends Activity {
     String msg = "Android : ";

     @Override
     protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         Log.d(msg, "This is an on create event");

     }

     @Override
     protected void onStart(){
         super.onStart();
         Log.d(msg, "This is a start event");
     }

     @Override
     protected void onResume(){
         super.onResume();
         Log.d(msg, "This is a resume event");
     }

     @Override
     protected void onStop(){
         super.onStop();
         Log.d(msg, "This is a stop event");
     }

     @Override
     protected void onDestroy(){
         super.onDestroy();
         Log.d(msg, "This is a destroy event");
     }
}


