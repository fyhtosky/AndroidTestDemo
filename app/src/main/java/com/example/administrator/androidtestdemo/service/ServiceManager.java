package com.example.administrator.androidtestdemo.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.List;

public class ServiceManager {

  public static final String TAG="ServiceManager";


    /**
     * 判断Service是否注册
     * @param context
     * @param serviceAction
     * @return
     */
    public static boolean isRegistererService(Context context,String serviceAction){
        PackageManager packageManager=context.getPackageManager();
        //指定要查询的Service Action
        Intent intent=new Intent(serviceAction);
        //查询服务
        @SuppressLint("WrongConstant")
        List<ResolveInfo>resolveInfos=packageManager.queryIntentServices(intent,PackageManager.GET_INTENT_FILTERS);
        if(resolveInfos.size()>0){
            ResolveInfo resolveInfo=resolveInfos.get(0);
            Log.i(TAG,"服务信息："+resolveInfo.toString());
            return true;
        }
        return false;
    }


    /**
     * 判断service是否正在运行
     * @param context
     * @param serviceNmae
     * @return
     */
    public static boolean isStartService(Context context,String serviceNmae){
        ActivityManager activityManager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //返回正在运行的Service
        List<ActivityManager.RunningServiceInfo> runningServiceInfos=activityManager.getRunningServices(100);
        for(int i=0;i<runningServiceInfos.size();i++){
            ActivityManager.RunningServiceInfo runningServiceInfo=runningServiceInfos.get(i);
            if(serviceNmae.equals(runningServiceInfo.service.getClassName())){
                Log.i(TAG,"服务正在运行："+runningServiceInfo.service.getClassName());
                return true;

            }
        }
        return false;
    }

}
