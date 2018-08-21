package com.example.administrator.diycode.fragment;

import android.os.Bundle;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.activity.base.BaseFragment;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.movie.bean.GankResp;
import com.example.network_sdk.movie.interfaces.GankListContract;
import com.example.network_sdk.movie.model.movieListModelImp;
import com.example.network_sdk.movie.presenter.MovieListPresenter;

public class MoiveFragment extends BaseFragment<movieListModelImp, MovieListPresenter>  implements GankListContract.View  {

    public static MoiveFragment newInstance() {
        Bundle args = new Bundle();
        MoiveFragment fragment = new MoiveFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.moive_fragment;
    }

    @Override
    protected void onInitView(Bundle bundle) {

    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getViewImp() {
        return null;
    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void getGankrList(GankResp gankResp) {

    }

    @Override
    public void showErrorWithStatus(String msg) {

    }
}
