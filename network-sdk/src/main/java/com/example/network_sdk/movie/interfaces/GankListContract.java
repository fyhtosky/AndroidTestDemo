package com.example.network_sdk.movie.interfaces;

import com.example.network_sdk.base.BaseModel;
import com.example.network_sdk.base.BasePresenter;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.movie.bean.GankResp;

import rx.Observable;

/**
 * Mvp模式的基类设计
 */
public interface GankListContract {
    /**
     * Weather列表获取的View界面展示
     */
    interface View extends BaseView{

        void getGankrList(GankResp gankResp);
    }

    /**
     * Weather的网络请求
     */
    interface Model extends BaseModel{
       Observable<GankResp> getGankList(String url);
    }

     abstract class Presenter extends BasePresenter<View,Model>{
         public abstract void  getPresenterGankList(String url);
    }

}
