package com.yan.campusbbs.util;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yan.campusbbs.repository.NetAddress;
import com.yan.campusbbs.util.entity.DownloadProListener;
import com.yan.campusbbs.util.entity.ProgressResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yan on 2016/12/12.
 */

public class AppRetrofit {
    private static AppRetrofit appRetrofit;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private List<DownloadProListener> progressListeners;

    public AppRetrofit addProgressListener(DownloadProListener progressListener) {
        this.progressListeners.add(progressListener);
        return appRetrofit;
    }


    public OkHttpClient getClient() {
        return okHttpClient;
    }

    @Inject
    public AppRetrofit() {
        progressListeners = new ArrayList<>();
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response orginalResponse = chain.proceed(chain.request());
                        Response response = orginalResponse.newBuilder().body(
                                new ProgressResponse(orginalResponse.body(), progressListeners)).build();
                        return response;
                    }
                })
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
//                .addNetworkInterceptor(mTokenInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(NetAddress.MAIN_PATH)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public void removeProgressListener(DownloadProListener proListener) {
        progressListeners.remove(proListener);

    }

    public Retrofit retrofit() {
        return retrofit;
    }
}
