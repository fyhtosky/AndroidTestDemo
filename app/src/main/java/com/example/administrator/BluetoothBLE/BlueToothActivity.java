package com.example.administrator.BluetoothBLE;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        ButterKnife.bind(this);
        init();
        Configuration config = getResources().getConfiguration();
        int smallestScreenWidth = config.smallestScreenWidthDp;
        Log.i("BlueToothActivity","smallest width : "+ smallestScreenWidth);
    }

    private void init() {
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
}
