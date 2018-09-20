package com.example.network_sdk.downLoad;

import rx.Subscriber;

public class UploadImagePresenter extends UploadImageContract.Presenter {
    @Override
    public void uploadImage(String url,String des) {
        addSubscribe(mModel.uploadUserFile(url,des).subscribe(new Subscriber<ProgressRequestBody>() {
            @Override
            public void onCompleted() {
              mView.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
               mView.onError();
            }

            @Override
            public void onNext(ProgressRequestBody progressRequestBody) {
                progressRequestBody.setListener(new ProgressRequestBody.Listener() {
                    @Override
                    public void onRequestProgress(long bytesWritten, long contentLength, long networkSpeed) {
                        mView.uploadProgress(bytesWritten, contentLength, bytesWritten * 1.0f / contentLength, networkSpeed);
                    }
                });

            }
        }));
    }
}
