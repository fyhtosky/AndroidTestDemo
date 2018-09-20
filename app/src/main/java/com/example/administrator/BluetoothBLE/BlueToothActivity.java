package com.example.administrator.BluetoothBLE;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Trace;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidtestdemo.R;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class BlueToothActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private FoundDeviceBroadcastReceiver foundDeviceBroadcastReceiver;
    @BindView(R.id.show_info)
    TextView showInfo;
    /**
     * 蓝牙核心类
     */
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.beginSection("BlueTooth");
        }
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
        Configuration config = getResources().getConfiguration();
        int smallestScreenWidth = config.smallestScreenWidthDp;
        Log.i("BlueToothActivity","smallest width : "+ smallestScreenWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            reportFullyDrawn();
        }
        BlueToothActivityPermissionsDispatcher.writeWithPermissionCheck(this);
    }

    private void init() {
        Debug.startMethodTracing("TestApp");
        //初始化
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        foundDeviceBroadcastReceiver=new FoundDeviceBroadcastReceiver();
         //注册广播
        IntentFilter filter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(foundDeviceBroadcastReceiver,filter);
        filter=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(foundDeviceBroadcastReceiver,filter);
        //获得所有已绑定的蓝牙设备
        Set<BluetoothDevice>bluetoothDevices=bluetoothAdapter.getBondedDevices();
        if(bluetoothDevices.size()>0){
            for (BluetoothDevice device :bluetoothDevices){
                //将已绑定的蓝牙名称和地址显示出来
                showInfo.append(device.getName()+":"+device.getAddress()+"\n");
            }
        }
    }


    @OnClick({R.id.open_blueTooth, R.id.search_blueTooth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_blueTooth:
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 1);
                break;
            case R.id.search_blueTooth:
                showProgressDialog("搜索设备","正在搜索。。。");
                //正在搜索设备则取消搜索
                if(bluetoothAdapter.isDiscovering()){
                    bluetoothAdapter.cancelDiscovery();
                }
                //开始搜索
                bluetoothAdapter.startDiscovery();
                break;
        }
    }

    /**
     * 显示Loading框
     *
     * @param title   title
     * @param message message
     */
    private void showProgressDialog(final String title, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setTitle(title);
                mProgressDialog.setMessage(message);
                mProgressDialog.show();
            }
        });
    }

    /**
     * 隐藏Loading框
     */
    private void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        });
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void write() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BlueToothActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void doWrite(final PermissionRequest request) {
        showRationaleDialog("使用此功能需要写入分析日志的权限", request);
    }


    /**
     * Created by kqw on 2016/8/2.
     * 蓝牙的广播接收者
     */
    public  class FoundDeviceBroadcastReceiver extends BroadcastReceiver {

        private static final String TAG = "FoundDeviceBroadcast";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    // 获取设备
                    BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Log.i(TAG, "onReceive: 发现新设备 : " + btDevice.getName() + "  MAC Address : " + btDevice.getAddress());
                    showInfo.append(btDevice.getName()+":"+btDevice.getAddress()+"\n");
                    dismissProgressDialog();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Log.i(TAG, "onReceive: 开始附近的蓝牙设备搜索");

                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.i(TAG, "onReceive: 结束附近的蓝牙设备搜索");

                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    switch (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF)) {
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Log.i(TAG, "onReceive: 正在断开蓝牙...");

                            break;
                        case BluetoothAdapter.STATE_OFF:
                            Log.i(TAG, "onReceive: 蓝牙已经断开");

                            break;
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Log.i(TAG, "onReceive: 正在打开蓝牙...");

                            break;
                        case BluetoothAdapter.STATE_ON:
                            Log.i(TAG, "onReceive: 蓝牙已经打开");

                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(foundDeviceBroadcastReceiver);
        Debug.stopMethodTracing();
    }

    /**
     * 显示权限的对话框
     *
     * @param messageResId
     * @param request
     */
    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }
}
