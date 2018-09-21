package com.example.network_sdk.downLoad;

import com.example.network_sdk.base.BaseModel;
import com.example.network_sdk.base.BasePresenter;
import com.example.network_sdk.base.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

public interface UploadImageContract {
    /**
     * Weather列表获取的View界面展示
     */
    interface View extends BaseView {
        /**
         * 下载之前的处理
         */
        void onUploadBefore();

        /**
         * 下载成功之后的处理
         */
        void onSuccess();

        /**
         * 异常的处理
         */
        void onError();

        /**
         * 执行上传过程中的进度回调，UI线程
         *
         * @param currentSize  当前下载的字节数
         * @param totalSize    总共需要下载的字节数
         * @param progress     当前下载的进度
         * @param networkSpeed 当前下载的速度 字节/秒
         */
         void uploadProgress(long currentSize, long totalSize, float progress, long networkSpeed);
    }

    interface Model extends BaseModel {
        Observable<uploadImageReturnBean> uploadUserFile(String url, String des);
    }
    abstract class Presenter extends BasePresenter<UploadImageContract.View,UploadImageContract.Model> {
        public abstract void  uploadImage(String url,String des);
    }
}
