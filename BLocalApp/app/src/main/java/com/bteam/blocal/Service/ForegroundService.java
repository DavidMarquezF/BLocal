package com.bteam.blocal.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";
    private static final String SERVICE_TASK_RESULT_COMPLETE = "SERVICE_TASK_RESULT_COMPLETE";
    private static final String EXTRA_KEY_BROADCAST_RESULT = "EXTRA_KEY_BROADCAST_RESULT";
    ExecutorService execService;
    private int i;
    MutableLiveData<Integer> sleepTime;
    MutableLiveData<Boolean> started;

    public ForegroundService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        i = 0;
        Log.d(TAG, "onCreate: Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(sleepTime == null){
            sleepTime = new MutableLiveData<Integer>();
        }
        if(started == null){
            started = new MutableLiveData<Boolean>();
            started.setValue(false);
        }
        sleepTime.setValue(60000);
        doBackgroundStuff();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        started.setValue(false);
        super.onDestroy();
    }

    private void doBackgroundStuff() {
        if(started.getValue() == false){
            started.setValue(true);
            doRecursiveWork();
        }

    }

    private void doRecursiveWork(){
        //final Handler handler = new Handler();
        if(execService == null){
            execService = Executors.newSingleThreadExecutor();
        }

        execService.submit(new Runnable() {
            @Override
            public void run() {
                while(started.getValue()) {
                    /*
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                     */
                    i++;
                    Log.d(TAG, "Work done: " + i);
                    try {
                        Thread.sleep(sleepTime.getValue());
                    } catch (InterruptedException e) {
                        Log.e(TAG, "ERROR: ", e);
                    }
                }
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
