package com.example.administrator.androidtestdemo.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.Imp.AndroidLogAdapter;
import com.example.Imp.DiskLogAdapter;
import com.example.Imp.FormatStrategy;
import com.example.Imp.PrettyFormatStrategy;
import com.example.administrator.androidtestdemo.BuildConfig;
import com.example.logger.Logger;
import com.facebook.drawee.backends.pipeline.Fresco;


public class MyApp extends Application {
    public static Context AppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        initLogStore();
        //图片加载
        Fresco.initialize(AppContext);
    }

    /**
     * 初始化日志库
     */
    private void initLogStore() {
       // 修改默认配置
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .tag("iiiiii")                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        /**
         * 控制打印的开关
         */
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });

         //保存log到文件 保存的路径： /storage/emulated/0
        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
