package com.highway.study.androidbase;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class BaseService extends Service {
    private static final String TAG = "BaseService";
    public BaseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind.....");
        return new Binder();
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate.....");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand.....");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy.....");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind.....");
        return false;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e(TAG,(intent == null) + ".......");
    }
}
