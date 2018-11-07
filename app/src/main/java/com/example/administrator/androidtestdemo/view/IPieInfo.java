package com.example.administrator.androidtestdemo.view;

import android.support.annotation.ColorInt;

public interface IPieInfo {
    double getValue();

    @ColorInt
    int getColor();

    String getDesc();
}
