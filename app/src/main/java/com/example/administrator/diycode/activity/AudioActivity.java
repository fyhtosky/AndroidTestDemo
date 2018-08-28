package com.example.administrator.diycode.activity;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.activity.base.BaseActivity;
import com.example.administrator.diycode.audioView.VisualFrequencyView;
import com.example.network_sdk.base.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioActivity extends BaseActivity {

    @BindView(R.id.frequency_view)
    VisualFrequencyView frequencyView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audio;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        ButterKnife.bind(this);
        frequencyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frequencyView.startResponse();
            }
        });
    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getView() {
        return null;
    }



}
