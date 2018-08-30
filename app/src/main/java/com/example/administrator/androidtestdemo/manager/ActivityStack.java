package com.example.administrator.androidtestdemo.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

public class ActivityStack {

    private static volatile ActivityStack instance=null;
    public ActivityStack() {
    }
    private static Stack<Activity> stack = new Stack<>();
    // 单例模式中获取唯一的MyApplication实例
    public static ActivityStack getInstance() {
        if (instance == null) {
            synchronized (ActivityStack.class) {
                if (instance == null) {
                    instance = new ActivityStack();
                }
            }

        }
        return instance;
    }
    /**
     * 添加元素
     */
    public synchronized void push(Activity activity) {
        stack.push(activity);
    }

    /**
     * 获取stack顶的Activity，但是不去除
     */
    public synchronized Activity peek() {
        return stack.peek();
    }

    /**
     * 去除并杀死栈顶的Activity
     */
    public synchronized void pop() {
        Activity activity = stack.pop();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 去除并杀死某个Activity（stack虽然是栈，但是它其实是继承了Vector，stack.remove(activity)其实是Vector的功能）
     */
    public synchronized void killActivity(Activity activity) {
        if (activity != null) {
            stack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    private synchronized boolean isEmpty() {
        return stack.isEmpty();
    }

    public synchronized void clear() {
        while (!isEmpty()) {
            pop();
        }
    }
    // 遍历所有Activity并finish
    public synchronized void exit() {
        for (Activity activity : stack) {
            if (activity != null) {
                activity.finish();
            }
        }
        stack.clear();
        instance=null;
    }
    /**
     * 退出应用程序
     */
    public synchronized void AppExit(Context context) {
        try {
            exit();
            ActivityManager manager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
