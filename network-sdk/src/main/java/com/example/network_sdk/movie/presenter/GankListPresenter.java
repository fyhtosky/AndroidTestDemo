package com.example.network_sdk.movie.presenter;

import com.example.network_sdk.movie.bean.GankResp;
import com.example.network_sdk.movie.interfaces.GankListContract;

import rx.Subscriber;

public class GankListPresenter extends GankListContract.Presenter {

    @Override
    public void getPresenterGankList(String url) {
        addSubscribe(mModel.getGankList(url).subscribe(new Subscriber<GankResp>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
               mView.showErrorWithStatus(e.getLocalizedMessage());
            }

            @Override
            public void onNext(GankResp gankResp) {
                  mView.getGankrList(gankResp);
            }
        }));
    }
}
