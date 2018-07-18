package com.example.network_sdk.base;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 1.获取绑定View实例传递到子类中进行调用
 * 2.注销View的实例
 * 3.创建Model的实例
 * 4.注销Model的实例
 * 5.通过RXjava进行Activity及Fragment生命周期绑定
 * @param <V>
 * @param <M>
 */
public class BasePresenter<V extends BaseView,M extends BaseModel> implements Presenter<V,M> {
    public Context mContext;
    protected V mView;
    protected M mModel;

    protected CompositeSubscription mCompositeSubscription;

    public void unSubscribe(){
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription){
        if(mCompositeSubscription==null){
            mCompositeSubscription=new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * 获取实例View
     * @return
     */
    public V getmView() {
        return mView;
    }


    /**
     * 获取实例Model
     * @return
     */
    public M getmModel() {
        return mModel;
    }


    /**
     * 绑定View
     * @param v
     */
    @Override
    public void attachView(V v) {
         this.mView=v;
    }

    /**
     * 绑定Model
     * @param m
     */
    @Override
    public void attachModel(M m) {
       this.mModel=m;
    }

    /**
     * 解绑View
     */
    @Override
    public void detachView() {
      this.mView=null;
    }


    /**
     * 解绑Model
     */
    @Override
    public void detachModel() {
       this.mModel=null;
    }



    /**
     * 判断是否绑定View
     * @return
     */
    public boolean isViewBind() {
        return mView!=null;
    }
}
