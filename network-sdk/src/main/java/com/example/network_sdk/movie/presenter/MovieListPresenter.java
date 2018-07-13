package com.example.network_sdk.movie.presenter;

import android.util.Log;

import com.example.network_sdk.movie.bean.Movie;
import com.example.network_sdk.movie.bean.MovieSubject;
import com.example.network_sdk.movie.interfaces.MovieListContract;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MovieListPresenter extends MovieListContract.Presenter{
    @Override
    public void getPresenterMovieList(int start, int count) {
          addSubscribe(mModel.getMovieList(start,count).subscribe(new Subscriber<MovieSubject>() {
              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable e) {
                mView.showErrorWithStatus(e.getLocalizedMessage());
              }

              @Override
              public void onNext(MovieSubject movieSubject) {
                 mView.getMovieList(movieSubject);
              }
          }));

    }
}
