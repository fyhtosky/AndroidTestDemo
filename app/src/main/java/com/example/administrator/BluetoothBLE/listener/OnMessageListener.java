package com.example.administrator.BluetoothBLE.listener;

/**
 * 蓝牙消息传递的监听
 */
public interface OnMessageListener {
    // 发送消息
    void onSend(String message);

    // 接收消息
    void onRead(String message);
}
