package com.example.administrator.diycode.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.activity.base.BaseFragment;
import com.example.administrator.androidtestdemo.gank.adapter.GankAdapter;
import com.example.administrator.androidtestdemo.gank.decoration.GankDecoration;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.movie.bean.GankResp;
import com.example.network_sdk.movie.interfaces.GankListContract;
import com.example.network_sdk.movie.model.GankListModelImp;
import com.example.network_sdk.movie.presenter.GankListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GankFragment extends BaseFragment<GankListModelImp, GankListPresenter> implements GankListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private GankAdapter gankAdapter;
    private static final String GANK_URL = "http://gank.io/api/data/福利/5000/1";
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
        unbinder = ButterKnife.bind(this, rootView);
        recyclerView.addItemDecoration(new GankDecoration());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setLayoutManager(manager);
        gankAdapter = new GankAdapter();
        recyclerView.setAdapter(gankAdapter);

    }

    public void quickToTop() {
        recyclerView.smoothScrollToPosition(0);
    }
    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @Override
    protected void lazyFetchData() {
        mPresenter.getPresenterGankList(GANK_URL);
    }

    @Override
    public void getGankrList(GankResp gankResp) {
        gankAdapter.setData(gankResp.getResults());
        gankAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorWithStatus(String msg) {
        Toast.makeText(getContext(), "出现异常：" + msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
