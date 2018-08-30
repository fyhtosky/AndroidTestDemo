package com.example.administrator.androidtestdemo.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.administrator.androidtestdemo.manager.ActivityStack;
import com.example.network_sdk.base.BaseModel;
import com.example.network_sdk.base.BasePresenter;
import com.example.network_sdk.base.BaseView;
import com.example.network_sdk.base.ContractProxy;

public  abstract class BaseActivity<M extends BaseModel,P extends BasePresenter> extends AppCompatActivity {

    //    定义Presenter
    protected P mPresenter;
    protected Toast mToast;
    //    获取布局资源文件
    protected abstract int getLayoutId();

//    初始化数据

    protected abstract void onInitView(Bundle bundle);

//    初始化事件Event

    protected abstract void onEvent();

    //   获取抽取View对象
    protected abstract BaseView getView();

    //    获得抽取接口Model对象
    protected Class getModelClazz() {
        return (Class<M>) ContractProxy.getModelClazz(getClass(), 0);
    }

    /* 获得抽取接口Presenter对象 */
    protected Class getPresenterClazz() {
        return (Class<P>) ContractProxy.getPresnterClazz(getClass(), 1);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().push(this);
        if (getLayoutId() != 0) {
//            设置布局资源文件
            setContentView(getLayoutId());
            bindMVP();
            onInitView(savedInstanceState);
            onEvent();

        }
    }

    /**
     * 获取presenter 实例
     */
    private void bindMVP() {
        if (getPresenterClazz() != null) {
            mPresenter = getPresenterImpl();
            mPresenter.mContext = this;
            bindVM();
        }
    }

    private <T> T getPresenterImpl() {
        return ContractProxy.getInstance().presenter(getPresenterClazz());
    }

    @Override
    protected void onStart() {
        if (mPresenter == null) {
            bindMVP();
        }
        super.onStart();
    }

    private void bindVM() {
        if (mPresenter != null && !mPresenter.isViewBind() && getModelClazz() != null && getView() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().bindView(getView(), mPresenter);
            mPresenter.mContext = this;
        }
    }

    /**
     * activity摧毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
            ContractProxy.getInstance().unbindView(getView(), mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), mPresenter);
            mPresenter = null;
        }
        ActivityStack.getInstance().killActivity(this);
    }

    protected void openActivity(Class<?> cls) {
        openActivity(this, cls);
    }

    public static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
    /**
     * 发出一个短Toast
     *
     * @param text 内容
     */
    public void toastShort(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 发出一个长toast提醒
     *
     * @param text 内容
     */
    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }


    private void toast(final String text, final int duration) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text, duration);
                    } else {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    }
                    mToast.show();
                }
            });
        }
    }
}
