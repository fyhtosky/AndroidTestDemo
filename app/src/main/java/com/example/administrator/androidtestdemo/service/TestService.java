package com.example.administrator.androidtestdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class TestService extends Service {


    /**
     * 创建服务
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG","开机启动的服务");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 销毁服务
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
