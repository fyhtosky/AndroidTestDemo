package com.example.administrator.BluetoothBLE.listener;

import android.bluetooth.BluetoothDevice;

/**
 * 客户端蓝牙连接的回调
 */
public interface OnServiceConnectListener {

    // 等待连接
    void onConnectListening();

    // 蓝牙连接成功
    void onConnectSuccess(BluetoothDevice device);

    // 连接失败
    void onConnectFail(Exception e);

    // 连接中断
    void onConnectLost(Exception e);
}
