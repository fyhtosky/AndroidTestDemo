package com.example.administrator.androidtestdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.androidtestdemo.activity.MainActivity;
import com.example.administrator.androidtestdemo.service.TestService;

public class BootBroadcastReceiver extends BroadcastReceiver {
    public final static String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    public BootBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)){
            // 后边的XXX.class就是要启动的服务
            Intent actIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            actIntent.setAction("android.intent.action.MAIN");
            actIntent.addCategory("android.intent.category.LAUNCHER");
            actIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(actIntent);
            Log.v("TAG", "开机自动服务自动启动.....");
            // 启动应用，参数为需要自动启动的应用的包名
            Intent serIntent= new Intent(context, TestService.class);
            serIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(serIntent);
            Log.v("TAG", "开机程序自动启动.....");
        }
    }
}
