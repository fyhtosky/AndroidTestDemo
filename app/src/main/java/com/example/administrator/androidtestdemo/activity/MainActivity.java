package com.example.administrator.androidtestdemo.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.tool.BitmapTools;
import com.example.logger.Logger;
import com.example.network_sdk.test.Test1;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_demo)
    TextView tvDemo;
    @BindView(R.id.iv_image)
    ImageView ivImage;


    /**
     * 拍照时存储拍照结果的临时文件
     */
    private File mTmpFile;
    //获取打开相机的权限
    private static final int OPEN_CAMERA = 2;
    //启动相册的标示
    public final static int REQUEST_CAMERA = 1;
    private Test1 test1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Logger.d("MainActivity.onCreate()");
        test1=new Test1();
        test1.initObservable(MainActivity.this);
//        test1.initObserverableOne(MainActivity.this);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openCamera() {
        showCamera();
    }

    /**
     * 选择相机
     */
    private void showCamera() {
        // 创建临时文件存储照片
        mTmpFile = new File(getExternalFilesDir("img"), "temp.jpg");
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uriForFile = FileProvider.getUriForFile(MainActivity.this, "com.example.administrator.androidtestdemo.fileProvider", mTmpFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            }
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(getApplicationContext(),
                    "没有相机", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowCamer(final PermissionRequest request) {
        showRationaleDialog("使用此功能需要打开照相机的权限", request);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bitmap bitmap = null;
            if (requestCode == REQUEST_CAMERA) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri inputUri = FileProvider.getUriForFile(MainActivity.this,
                            "com.example.administrator.androidtestdemo.fileProvider", mTmpFile);
                    bitmap = BitmapTools.getBitmap(getContentResolver(), inputUri);
                } else {
                    bitmap = BitmapTools.getBitmap(mTmpFile.getAbsolutePath());
                }
                /**
                 * 将图片旋转90度
                 */
                bitmap = BitmapTools.rotateBitmapByDegree(bitmap, 90);
                ivImage.setImageBitmap(bitmap);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.tv_demo,R.id.bt_start_movie,R.id.bt_start_gank})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_demo:
                MainActivityPermissionsDispatcher.openCameraWithPermissionCheck(MainActivity.this);
                break;
            case R.id.bt_start_movie:
                startActivity(new Intent(MainActivity.this,MovieActivity.class));
                break;
            case  R.id.bt_start_gank:
                startActivity(new Intent(MainActivity.this,GankActivity.class));
                break;
        }

    }


}
