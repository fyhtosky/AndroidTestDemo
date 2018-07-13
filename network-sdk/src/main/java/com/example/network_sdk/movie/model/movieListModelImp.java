package com.example.network_sdk.movie.model;

import com.example.network_sdk.base.RetrofitFactory;
import com.example.network_sdk.movie.MovieApi;
import com.example.network_sdk.movie.bean.MovieSubject;
import com.example.network_sdk.movie.interfaces.MovieListContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class movieListModelImp implements MovieListContract.Model {
    @Override
    public Observable<MovieSubject> getMovieList(int start, int count) {
        return RetrofitFactory.getRetrofit().create(MovieApi.class).getTop250(start,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}



