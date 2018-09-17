package com.example.administrator.androidtestdemo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.androidtestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url="https://www.baidu.com";
    private WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        webSettings = webView.getSettings();
        if(isNetWorkUrl(url)){
            webView.loadUrl(url);

            /**
             * WebViewClient主要帮助WebView处理各种通知、请求事件的，有以下常用方法：
             - onPageFinished 页面请求完成
             - onPageStarted 页面开始加载
             - shouldOverrideUrlLoading 拦截url
             - onReceivedError 访问错误时回调，例如访问网页时报错404，在这个方法回调的时候可以加载错误页面。

             WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等，有以下常用方法。
             - onJsAlert webview不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
             - onReceivedTitle 获取网页标题
             - onReceivedIcon 获取网页icon
             - onProgressChanged 加载进度回调
             */
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            //支持js脚本
             webSettings.setJavaScriptEnabled(true);



        }

    }


    /**
     * 检测Url地址是否有效
     * @return
     */
    private boolean isNetWorkUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (!Patterns.WEB_URL.matcher(url.toString()).matches()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

}
