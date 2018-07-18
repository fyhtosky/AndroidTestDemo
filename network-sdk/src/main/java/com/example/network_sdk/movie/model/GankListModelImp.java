package com.example.network_sdk.movie.model;

import com.example.network_sdk.base.RetrofitFactory;
import com.example.network_sdk.movie.GankApi;
import com.example.network_sdk.movie.bean.GankResp;
import com.example.network_sdk.movie.interfaces.GankListContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GankListModelImp implements GankListContract.Model {


    @Override
    public Observable<GankResp> getGankList(String url) {
        return RetrofitFactory.getRetrofit().create(GankApi.class).getGank(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
