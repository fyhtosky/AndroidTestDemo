package com.example.network_sdk.base;

import android.support.annotation.NonNull;

import com.example.network_sdk.net.Interceptor.HttpCommonInterceptor;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory<Service> {
    private static final Object Object = new Object();
    private volatile static Retrofit retrofit;
    protected Service mService;

    public RetrofitFactory(Service mService) {
       retrofit=getRetrofit();
        this.mService = retrofit.create(getServiceClass());
    }

    private Class<Service> getServiceClass() {
        return (Class<Service>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @NonNull
    public static Retrofit getRetrofit() {

        synchronized (Object) {
            if (retrofit == null) {

                // 设置 Log 拦截器，可以用于以后处理一些异常情况
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                // 为所有请求自动添加 token
//                Interceptor mTokenInterceptor = new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request originalRequest = chain.request();
//                        // 如果当前没有缓存 token 或者请求已经附带 token 了，就不再添加
//                        if (null == mCacheUtil.getToken() || alreadyHasAuthorizationHeader(originalRequest)) {
//                            return chain.proceed(originalRequest);
//                        }
//                        String token = OAuth.TOKEN_PREFIX + mCacheUtil.getToken().getAccess_token();
//                        // 为请求附加 token
//                        Request authorised = originalRequest.newBuilder()
//                                .header(OAuth.KEY_TOKEN, token)
//                                .build();
//                        return chain.proceed(authorised);
//                    }
//                };
//
//                // 自动刷新 token
//                Authenticator mAuthenticator = new Authenticator() {
//                    @Override
//                    public Request authenticate(Route route, Response response) {
//                        TokenService tokenService = mRetrofit.create(TokenService.class);
//                        String accessToken = "";
//                        try {
//                            if (null != mCacheUtil.getToken()) {
//                                Call<Token> call = tokenService.refreshToken(OAuth.client_id,
//                                        OAuth.client_secret, OAuth.GRANT_TYPE_REFRESH,
//                                        mCacheUtil.getToken().getRefresh_token());
//                                retrofit2.Response<Token> tokenResponse = call.execute();
//                                Token token = tokenResponse.body();
//                                if (null != token) {
//                                    mCacheUtil.saveToken(token);
//                                    accessToken = token.getAccess_token();
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return response.request().newBuilder()
//                                .addHeader(OAuth.KEY_TOKEN, OAuth.TOKEN_PREFIX + accessToken)
//                                .build();
//                    }
//                };

                HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
                        .addHeaderParams("paltform","android")
                        .addHeaderParams("userToken","1234343434dfdfd3434")
                        .addHeaderParams("userId","123445")
                        .build();





                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true);


                retrofit = new Retrofit.Builder()
                        .baseUrl(Api.HOST)
                        .client(builder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
}
