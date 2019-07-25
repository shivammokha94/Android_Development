package com.example.myapplication;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class myservice extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent){

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "Started service", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Service closed", Toast.LENGTH_LONG).show();
    }
}

