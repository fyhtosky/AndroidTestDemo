package com.example.administrator.androidtestdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.moive.adapter.MovieAdapter;
import com.example.administrator.androidtestdemo.moive.decoration.MovieDecoration;
import com.example.network_sdk.base.BaseActivity;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.movie.bean.MovieSubject;
import com.example.network_sdk.movie.interfaces.MovieListContract;
import com.example.network_sdk.movie.model.movieListModelImp;
import com.example.network_sdk.movie.presenter.MovieListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieActivity extends BaseActivity<movieListModelImp, MovieListPresenter> implements MovieListContract.View {


    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        ButterKnife.bind(this);
        mainToolbar.setTitle(R.string.movie_list);
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.addItemDecoration(new MovieDecoration());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mMovieAdapter = new MovieAdapter();
        recyclerView.setAdapter(mMovieAdapter);

        mPresenter.getPresenterMovieList(0, 50);
    }

    @Override
    protected void onEvent() {



    }

    @Override
    protected BaseView getView() {
        return this;
    }


    @Override
    public void getMovieList(MovieSubject movieSubject) {
        Log.i("MovieActivity", movieSubject.toString());
        mMovieAdapter.setMovies(movieSubject.getSubjects());
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorWithStatus(String msg) {
        Toast.makeText(this, "出现异常：" + msg, Toast.LENGTH_SHORT).show();

    }


}
