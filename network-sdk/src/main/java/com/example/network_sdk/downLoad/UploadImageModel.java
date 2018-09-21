package com.example.network_sdk.downLoad;

import com.example.network_sdk.base.RetrofitFactory;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class UploadImageModel implements UploadImageContract.Model {
    @Override
    public Observable<uploadImageReturnBean> uploadUserFile(String url, String des) {
        //构建文件描述的RequestBody
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), des);
        //根据图片路径构建图片的RequestBody
        File file = new File(url);
        RequestBody imgbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        ProgressRequestBody requestBody=new ProgressRequestBody(imgbody);
        return RetrofitFactory.getRetrofit().create(UploadApi.class).uploadUserFile(description,requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


}
