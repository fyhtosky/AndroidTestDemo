package com.example.administrator.androidtestdemo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.androidtestdemo.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrescoDemoActivity extends AppCompatActivity {

    @BindView(R.id.drawee_1)
    SimpleDraweeView drawee1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_demo);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .build();
        drawee1.setHierarchy(hierarchy);
        Uri uri = Uri.parse("http://ww2.sinaimg.cn/large/7a8aed7bgw1es8c7ucr0rj20hs0qowhl.jpg");
        drawee1.setImageURI(uri);
    }
}
