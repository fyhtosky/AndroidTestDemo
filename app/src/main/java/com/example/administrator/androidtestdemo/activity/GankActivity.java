package com.example.administrator.androidtestdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.gank.adapter.GankAdapter;
import com.example.administrator.androidtestdemo.gank.decoration.GankDecoration;
import com.example.network_sdk.base.BaseActivity;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.movie.bean.GankResp;
import com.example.network_sdk.movie.interfaces.GankListContract;
import com.example.network_sdk.movie.model.GankListModelImp;
import com.example.network_sdk.movie.presenter.GankListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GankActivity extends BaseActivity<GankListModelImp, GankListPresenter> implements GankListContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private GankAdapter gankAdapter;
    private static final String GANK_URL = "http://gank.io/api/data/福利/5000/1";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        ButterKnife.bind(this);
        toolBar.setTitle(R.string.award_list);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.addItemDecoration(new GankDecoration());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setLayoutManager(manager);
        gankAdapter = new GankAdapter();
        recyclerView.setAdapter(gankAdapter);

        mPresenter.getPresenterGankList(GANK_URL);
    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getView() {
        return this;
    }


    @Override
    public void getGankrList(GankResp gankResp) {
        gankAdapter.setData(gankResp.getResults());
        gankAdapter.notifyDataSetChanged();

    }

    @Override
    public void showErrorWithStatus(String msg) {
        Toast.makeText(this, "出现异常：" + msg, Toast.LENGTH_SHORT).show();
    }


}
