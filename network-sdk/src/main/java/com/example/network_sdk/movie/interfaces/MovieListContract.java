package com.example.network_sdk.movie.interfaces;

import android.content.Context;

import com.example.network_sdk.base.BaseModel;
import com.example.network_sdk.base.BasePresenter;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.movie.bean.MovieSubject;

import rx.Observable;

/**
 * Mvp模式的基类设计
 */
public interface MovieListContract {
    /**
     * 电影列表获取的View界面展示
     */
    interface View extends BaseView{
        void getMovieList(MovieSubject movieSubject);

    }

    /**
     * 电影列表的网络请求
     */
    interface Model extends BaseModel{
       Observable<MovieSubject> getMovieList(int start, int count);

    }

     abstract class Presenter extends BasePresenter<View,Model>{
         public abstract void  getPresenterMovieList(int start, int count);

    }

}
