package com.yan.campusbbs.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.BuildConfig;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.repository.entity.download.DownloadProListener;
import com.yan.campusbbs.repository.entity.download.ProgressResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by yan on 2016/12/12.
 */

public class AppRetrofit {
    private static AppRetrofit appRetrofit;
    private static boolean IS_LOG = true;

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private List<DownloadProListener> progressListeners;

    private DownLoadInterceptor downloadInterceptor;


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
        downloadInterceptor = new DownLoadInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        File cacheFile = new File(ApplicationCampusBBS.getApplication()
                .getCacheDir().getAbsolutePath(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheFile, cacheSize);
        builder.cache(cache);
        if (BuildConfig.DEBUG && IS_LOG) {
            builder.addInterceptor(getLogInterceptor());
        }

        okHttpClient = builder
                .addInterceptor(addHeadersInterceptor)
                .addNetworkInterceptor(downloadInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(DataAddress.MAIN_PATH)
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

    public Interceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        boolean isJson;

                        try {
                            new JsonParser().parse(message);
                            isJson = true;
                        } catch (JsonParseException e) {
                            isJson = false;
                        }

                        Log.e("AppRetrofit", isJson ? format(message) : message);
                    }
                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    private class DownLoadInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response orginalResponse = chain.proceed(chain.request());
            Response response = orginalResponse.newBuilder().body(
                    new ProgressResponse(orginalResponse.body(), progressListeners)).build();
            return response;
        }
    }

    private Interceptor addHeadersInterceptor =
            chain -> {

                Headers headers = new Headers.Builder()
                        .add("jsessionId", TextUtils.isEmpty(ApplicationCampusBBS.getApplication().getSessionId())
                                ? "api_token_26deda60f5d446bf9fcf30f3286009a9"
                                : ApplicationCampusBBS.getApplication().getSessionId())
                        .build();
                Request request = chain.request()
                        .newBuilder()
                        .headers(headers)
                        .build();
                return chain.proceed(request);
            };

    private String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
