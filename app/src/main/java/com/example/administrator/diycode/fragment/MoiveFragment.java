package com.example.administrator.diycode.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.activity.base.BaseFragment;
import com.example.administrator.androidtestdemo.moive.adapter.MovieAdapter;
import com.example.administrator.androidtestdemo.moive.decoration.MovieDecoration;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.movie.bean.GankResp;
import com.example.network_sdk.movie.bean.MovieSubject;
import com.example.network_sdk.movie.interfaces.GankListContract;
import com.example.network_sdk.movie.interfaces.MovieListContract;
import com.example.network_sdk.movie.model.movieListModelImp;
import com.example.network_sdk.movie.presenter.MovieListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MoiveFragment extends BaseFragment<movieListModelImp, MovieListPresenter> implements MovieListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private MovieAdapter mMovieAdapter;

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
        unbinder = ButterKnife.bind(this, rootView);
        recyclerView.addItemDecoration(new MovieDecoration());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mMovieAdapter = new MovieAdapter();
        recyclerView.setAdapter(mMovieAdapter);
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
        mPresenter.getPresenterMovieList(0, 50);
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

    @Override
    public void getMovieList(MovieSubject movieSubject) {
        Log.i("MovieActivity", movieSubject.toString());
        mMovieAdapter.setMovies(movieSubject.getSubjects());
        mMovieAdapter.notifyDataSetChanged();
    }
}
