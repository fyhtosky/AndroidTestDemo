package com.example.administrator.BluetoothBLE.listener;

import android.bluetooth.BluetoothDevice;

/**
 * 服务端蓝牙连接的回调
 */
public interface OnClientConnectListener {
    // 正在连接
    void onConnecting();

    // 连接成功
    void onConnectSuccess(BluetoothDevice device);

    // 连接失败
    void onConnectFail(Exception e);

    // 连接中断
    void onConnectLost(Exception e);
}
