package com.yan.campusbbs.util;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.repository.entity.download.DownloadProListener;
import com.yan.campusbbs.repository.entity.download.ProgressResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by yan on 2016/12/12.
 */

public class AppRetrofit {
    private static AppRetrofit appRetrofit;
    private static int LOG_MODE = 2;//0. no log / 1. request / 2. use body

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private List<DownloadProListener> progressListeners;

    private DownLoadInterceptor downloadInterceptor;
    private Interceptor logInterceptor;


    public AppRetrofit addProgressListener(DownloadProListener progressListener) {
        this.progressListeners.add(progressListener);
        return appRetrofit;
    }

    public static AppRetrofit getAppRetrofit() {
        if (appRetrofit == null) {
            synchronized (AppRetrofit.class) {
                if (appRetrofit == null) {
                    appRetrofit = new AppRetrofit();
                }
            }
        }
        return appRetrofit;
    }

    public OkHttpClient getClient() {
        return okHttpClient;
    }

    private AppRetrofit() {
        progressListeners = new ArrayList<>();
        downloadInterceptor = new DownLoadInterceptor();

        if (LOG_MODE == 1) {
            logInterceptor = new OkHttpLogInterceptor();
        } else if (LOG_MODE == 2) {
            logInterceptor = new OkHttpLogInterceptor2();
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (LOG_MODE != 0) {
            builder.addInterceptor(logInterceptor);
        }
        okHttpClient = builder
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


    private class DownLoadInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response orginalResponse = chain.proceed(chain.request());
            Response response = orginalResponse.newBuilder().body(
                    new ProgressResponse(orginalResponse.body(), progressListeners)).build();
            return response;
        }
    }


    private class OkHttpLogInterceptor implements Interceptor {
        private static final String TAG = "OkHttpLogInterceptor";
        List<String> requestUrl;

        public OkHttpLogInterceptor() {
            requestUrl = new ArrayList<>();
        }

        public void add(final Request request) {
            if (!progressListeners.isEmpty()) return;
            if (requestUrl.contains(request.url().url().toString())) return;
            Log.e(TAG, "request: " + request.toString());
            requestUrl.add(request.url().url().toString());
            okhttp3.Call call = getClient().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.e(TAG, "onFailure: " + e);
                    requestUrl.remove(request.url().url().toString());
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    Log.e(TAG, "onResponse: " +format(response.body().string()) );
                    requestUrl.remove(request.url().url().toString());
                }
            });
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            add(request);
            return chain.proceed(request);
        }
    }

    private class OkHttpLogInterceptor2 implements Interceptor {
        private static final String TAG = "OkHttpLogInterceptor2";

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.e(TAG, "request: " + request.toString());
            Response response = chain.proceed(request);
            okhttp3.MediaType mediaType = response.body().contentType();
            Log.e(TAG, "mediaType.type: " + mediaType.toString());
            if (mediaType.equals(MediaType.parse("application/json;charset=UTF-8"))
                    || mediaType.equals(MediaType.parse("text/plain;charset=UTF-8"))) {
                String content = response.body().string();
                Log.e(TAG, "onResponse: " + format(content));
                return response.newBuilder().body((okhttp3.ResponseBody
                        .create(mediaType, content))).build();
            }
            return response;
        }
    }

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
