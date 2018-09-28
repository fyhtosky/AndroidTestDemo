package com.example.administrator.androidtestdemo.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.activity.base.BaseActivity;
import com.example.administrator.androidtestdemo.chain.ChainOfResponsibilityClient;
import com.example.administrator.androidtestdemo.chain.CustomInterceptor;
import com.example.administrator.androidtestdemo.chain.Request;
import com.example.administrator.androidtestdemo.chain.Result;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.downLoad.UploadImageContract;
import com.example.network_sdk.downLoad.UploadImageModel;
import com.example.network_sdk.downLoad.UploadImagePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadImageActivity extends BaseActivity<UploadImageModel, UploadImagePresenter> implements UploadImageContract.View {

    @BindView(R.id.start)
    Button start;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload_image;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        ButterKnife.bind(this);
    }

    @Override
    protected void onEvent() {
        Request request = new Request.Builder()
                .name("张三")
                .days(5)
                .reason("事假")
                .build();
        ChainOfResponsibilityClient client = new ChainOfResponsibilityClient();
        //添加拦截器
        client.addRatifys(new CustomInterceptor());
        Result result = client.execute(request);
        System.out.println("结果：" + result.toString());


    }

    @Override
    protected BaseView getView() {
        return this;
    }


    @Override
    public void onUploadBefore() {

    }

    @Override
    public void onSuccess() {
          toastShort("上传成功");
    }

    @Override
    public void onError() {
        toastShort("上传失败");

    }

    @Override
    public void uploadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
       tvInfo.append("当前的进度："+currentSize+";总文件大小："+totalSize+";进度："+progress+";上传的速度："+networkSpeed+"\n");
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }



    @OnClick(R.id.start)
    public void onViewClicked() {

        mPresenter.uploadImage("/sdcard/sc.png","headerValue1");
    }
}
