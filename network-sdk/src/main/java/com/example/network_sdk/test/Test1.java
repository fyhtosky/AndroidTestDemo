package com.example.network_sdk.test;

import android.content.Context;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class Test1 {

    /**
     * 初始化观察者
     * Observer 即观察者，它决定事件触发的时候将有怎样的行为
     * @param context
     * @return
     */
    public  Observer<String>  init(final Context context){
        Observer<String>observer=new Observer<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(context,"onCompleted",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,"onError",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(context,"onNext:"+s,Toast.LENGTH_SHORT).show();
            }
        };
        return observer;
    }


    /**
     * 初始化观察者
     * Observer 即观察者，它决定事件触发的时候将有怎样的行为
     * Subscriber是Observer的抽象类
     * @param context
     * @return
     */
    public Subscriber<String> initOne(final Context context){
        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onStart() {
                Toast.makeText(context,"initOne:onStart",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                Toast.makeText(context,"initOne:onCompleted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,"initOne:onError",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNext(String s) {
            Toast.makeText(context,"initOne:onNext:"+s,Toast.LENGTH_SHORT).show();

            }
        };
             return subscriber;
    }


   public void initObservable(Context context){
       Observable observable=Observable.create(new Observable.OnSubscribe<String>() {
           @Override
           public void call(Subscriber<? super String> subscriber) {
               subscriber.onNext("被观察变化了，我被动的发生变化了");
               subscriber.onCompleted();
           }

       });
       observable.subscribe(init(context));

    }
    public void initObserverableOne (Context context){
        Observable observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("小偷偷东西了，警察出动，抓小偷了");
                subscriber.onCompleted();
            }

        });
        observable.subscribe(initOne(context));
    }

}
