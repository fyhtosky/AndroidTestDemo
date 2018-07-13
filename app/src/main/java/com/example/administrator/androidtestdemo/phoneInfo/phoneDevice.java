package com.example.administrator.androidtestdemo.phoneInfo;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;

/**
 * 1.设备硬件参数相关信息
 * 2.硬件状态相关信息
 */
public class phoneDevice {
    private phoneDevice phoneDevice;
    private Context mContext;


    public static final int CONNECT_UNREACHABLE = -1;
    public static final int CONNECT_WIFI = 0;
    public static final int CONNECT_3G = 1;
    public static final int CONNECT_GPRS = 2;
    public static final int CONNECT_4G = 3;
    public static final int CONNECT_UNKNOWN = 4;

    /**
     * 震动器
     */
    private Vibrator vibrator;

    public phoneDevice(Context context) {
        this.mContext = context;
        phoneDevice=this;
    }

    /**
     * 启动震动并持续指定的时间
     * 1.需要添加权限
     * @param milliseconds
     */
    public void vibrate(long milliseconds) {
        if (milliseconds> 0) {
            try {
                if (null == vibrator) {
                    vibrator = (Vibrator) mContext
                            .getSystemService(Service.VIBRATOR_SERVICE);
                }
                vibrator.vibrate(milliseconds);
            } catch (SecurityException e) {
                Toast.makeText(mContext, "没有开启震动器的权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 关闭启动 震动器
     */
    public void cancelVibrate() {
        if (null != vibrator) {
            vibrator.cancel();
        }
    }



    /**
     * 设置屏幕方向1为竖屏，0为横屏
     * @param params
     */
    public void setOrientation(int params) {

        if(params== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else if(params==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    /**
     * 获得设备CPU的频率
     *
     * @return
     */
    private String getCPUFrequency() {
        String result = "";
        LineNumberReader isr = null;
        try {
            Process pp = Runtime
                    .getRuntime()
                    .exec("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq");
            isr = new LineNumberReader(new InputStreamReader(
                    pp.getInputStream()));
            String line = isr.readLine();
            if (line != null && line.length() > 0) {
                try {
                    result = Integer.parseInt(line.trim()) / 1000 + "MHZ";
                } catch (Exception e) {
                  Log.e("phoneDevice","EUExDeviceInfo---getCPUFrequency()---NumberFormatException ");
                }
            } else {
                result = "0";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获得设备的IMEI号
     */
    private String getDeviceIMEI() {
        String imei = "unknown";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null
                    && telephonyManager.getDeviceId() != null) {
                imei = telephonyManager.getDeviceId();
            }
        } catch (SecurityException e) {
            Toast.makeText(mContext, "没有权限",
                    Toast.LENGTH_SHORT).show();
        }
        return imei;
    }
    /**
     * 获得设备的序列号
     * @return
     */
    private String getSimSerialNumber(){
        String serialNumber = "unknown";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null
                    && telephonyManager.getSimSerialNumber() != null) {
                serialNumber = telephonyManager.getSimSerialNumber();
            }
        } catch (SecurityException e) {
            Toast.makeText(mContext, "没有权限", Toast.LENGTH_SHORT).show();
        }
        return serialNumber;
    }

    /**
     * 获取设备的手机号
     * @return
     */
    private String getPhoneNumber(){
        String phoneNumber = "unknown";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null
                    && telephonyManager.getSimSerialNumber() != null) {
                phoneNumber = telephonyManager.getLine1Number();
            }
        } catch (SecurityException e) {
            Toast.makeText(mContext, "没有权限", Toast.LENGTH_SHORT).show();
        }
        return phoneNumber;
    }



    /**
     * 检测是否支持触屏
     * @return
     */
    private boolean getTouchScreenType() {
        //默认不支持触屏的
        boolean type =false;
        switch (mContext.getResources().getConfiguration().touchscreen) {
            case Configuration.TOUCHSCREEN_FINGER:// 电容屏
                type =true;
                break;
            case Configuration.TOUCHSCREEN_STYLUS:// 电阻屏
                type = true;
                break;
            case Configuration.TOUCHSCREEN_NOTOUCH:// 非触摸屏
                type =false;
                break;
        }
        return type;
    }


    /**
     * 检测设备是否支持蓝牙
     * @return
     */
    private boolean getBlueToothSupport(){
        boolean supported=false;
        // android从2.0(API level=5)开始提供蓝牙API，getDefaultAdapter返回适配器不为空则证明支持蓝牙
        try {
            if (Build.VERSION.SDK_INT >= 5) {
                final Class<?> btClass = Class
                        .forName("android.bluetooth.BluetoothAdapter");
                final Method method = btClass.getMethod("getDefaultAdapter",
                        new Class[] {});
                if (method.invoke(btClass, new Object[] {}) != null) {
                    supported =true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supported;

    }

    /**
     * 检测设备是否支出wifi
     * @return
     */
    private boolean getWIFISupport(){
        //默认不支持wifi
        boolean supported=false;
        if ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE) != null) {// 支持WIFI
            supported =true;
        }
        return  supported;
    }

    /**
     * 检测设备是否支持相机
     */
    private boolean getCameraSupport(){
        //默认不支持相机
        boolean supported=false;
        try {
            Camera camera = Camera.open();
            if (camera != null) {// 支持摄像头
               supported=true;
                camera.release();
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "没有权限",
                    Toast.LENGTH_SHORT).show();
        }
        return supported;
    }


    /**
     * 检测是否支持GPS定位
     * @return
     */
    private boolean getGPSSupport() {
        boolean support = false;// 默认不支持GPS
        try {
            LocationManager locationManager = (LocationManager) mContext
                    .getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {// 支持GPS
                support = true;
            }
        } catch (SecurityException e) {
            Toast.makeText(mContext, "没有权限",
                    Toast.LENGTH_SHORT).show();
        }

        return support;
    }


    /**
     * 检测设备的移动数据网络是否打开
     * @return
     */
    private boolean getMobileDataNetworkSupport() {
        // 默认未打开数据网络
        boolean support =false;
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null
                        && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    support = true;
                }
            }
        } catch (SecurityException e) {
            Toast.makeText(mContext, "没有权限",
                    Toast.LENGTH_SHORT).show();
        }
        return support;
    }



    /**
     * 获取电信运营商的名称
     */
    private String getMobileOperatorName() {
        String name = "unKnown";
        TelephonyManager telephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            // IMSI 国际移动用户识别码（IMSI：International Mobile Subscriber
            // Identification
            // Number）是区别移动用户的标志，
            // 储存在SIM卡中，可用于区别移动用户的有效信息。
            // IMSI由MCC、MNC组成，
            // 其中MCC为移动国家号码，由3位数字组成唯一地识别移动客户所属的国家，我国为460；
            // MNC为网络id，由2位数字组成, 用于识别移动客户所归属的移动网络，中国移动为00和02，中国联通为01,中国电信为03
            String imsi = telephonyManager.getNetworkOperator();
            if (imsi.equals("46000") || imsi.equals("46002")) {
                name = "中国移动";
            } else if (imsi.equals("46001")) {
                name = "中国联通";
            } else if (imsi.equals("46003")) {
                name = "中国电信";
            } else {
                // 其他电信运营商直接显示其名称，一般为英文形式
                name = telephonyManager.getSimOperatorName();
            }
        }
        return name;
    }

    /**
     * 获取网络状态类型
     * @param context
     * @return
     */
    private int getNetworkStatus(Context context) {
        int status = CONNECT_UNREACHABLE;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    switch (info.getType()) {
                        case ConnectivityManager.TYPE_MOBILE:
                            TelephonyManager telephonyManager = (TelephonyManager) context
                                    .getSystemService(Context.TELEPHONY_SERVICE);
                            switch (telephonyManager.getNetworkType()) {
                                case TelephonyManager.NETWORK_TYPE_1xRTT:
                                case TelephonyManager.NETWORK_TYPE_CDMA:
                                case TelephonyManager.NETWORK_TYPE_EDGE:
                                case TelephonyManager.NETWORK_TYPE_GPRS:
                                    status = CONNECT_GPRS;
                                    break;
                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                case TelephonyManager.NETWORK_TYPE_HSDPA:
                                case TelephonyManager.NETWORK_TYPE_HSPA:
                                case TelephonyManager.NETWORK_TYPE_HSUPA:
                                case TelephonyManager.NETWORK_TYPE_UMTS:
                                    status = CONNECT_3G;
                                    break;
                                case TelephonyManager.NETWORK_TYPE_LTE:
                                case TelephonyManager.NETWORK_TYPE_EHRPD:
                                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                case TelephonyManager.NETWORK_TYPE_HSPAP:
                                case TelephonyManager.NETWORK_TYPE_IDEN:
                                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                                    status = CONNECT_4G;
                                    break;
                                default:
                                    status = CONNECT_UNKNOWN;
                                    break;
                            }
                            break;
                        case ConnectivityManager.TYPE_WIFI:
                            status = CONNECT_WIFI;
                            break;
                    }
                }
            }
        } catch (SecurityException e) {
            Toast.makeText(context, "没有权限",
                    Toast.LENGTH_SHORT).show();
        }
        return status;
    }
}
