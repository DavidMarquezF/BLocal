package com.bteam.blocal.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";
    private ExecutorService execService;
    private int i;
    private int sleepTime;
    private boolean started;
    private Future<?> currentTask;


    public ForegroundService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        i = 0;
        sleepTime = 1000;
        Log.d(TAG, "onCreate: Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null && !started){
            doBackgroundWork();
            started = true;
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        started = false;
        if(currentTask != null){
            currentTask.cancel(true);
        }
        super.onDestroy();
    }


    private void doBackgroundWork(){
        if(execService == null){
            execService = Executors.newSingleThreadExecutor();
        }

        if(currentTask != null && currentTask.isCancelled()){
            return;
        }
        currentTask = execService.submit(new Runnable() {
            @Override
            public void run() {
                while(started) {
                    i++;
                    Log.d(TAG, "Work done: " + i);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "ERROR: ", e);
                        return;
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
