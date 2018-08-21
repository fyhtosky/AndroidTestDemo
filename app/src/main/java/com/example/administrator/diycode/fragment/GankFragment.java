package com.example.administrator.diycode.fragment;

import android.os.Bundle;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.activity.base.BaseFragment;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.movie.bean.GankResp;
import com.example.network_sdk.movie.interfaces.GankListContract;
import com.example.network_sdk.movie.model.GankListModelImp;
import com.example.network_sdk.movie.presenter.GankListPresenter;

public class GankFragment extends BaseFragment<GankListModelImp,GankListPresenter>  implements GankListContract.View {

    public static GankFragment newInstance() {
        Bundle args = new Bundle();
        GankFragment fragment = new GankFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.gank_fragment;
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
