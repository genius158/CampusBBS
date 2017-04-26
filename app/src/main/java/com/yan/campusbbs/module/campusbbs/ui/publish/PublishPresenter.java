package com.yan.campusbbs.module.campusbbs.ui.publish;

import android.content.Context;
import android.util.Log;

import com.yan.campusbbs.module.campusbbs.api.Publish;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.util.AppRetrofit;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishPresenter implements PublishContract.Presenter {
    private PublishContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    public PublishPresenter(Context context, PublishContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
    }

    @Override
    public void start() {

    }

    @Override
    public void publish(String topicTitle, String topicContent, String typeDiv, String topicLabel, String contentDiv, String filePath) {
        File file = new File(filePath);
        Log.e("publish: ", "publish");
        if (file.exists()) {
            Log.e("publish: ", "file.exists");
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file));
                    //添加其它信息
            builder.addFormDataPart("topicTitle", topicTitle);
            builder.addFormDataPart("topicContent", topicContent);
            builder.addFormDataPart("typeDiv", typeDiv);
            builder.addFormDataPart("topicLabel", topicLabel);
            builder.addFormDataPart("contentDiv", contentDiv);

            MultipartBody requestBody = builder.build();
            //构建请求
            Request request = new Request.Builder()
                    .url(DataAddress.MAIN_PATH + DataAddress.URL_PUBLISH)//地址
                    .post(requestBody)//添加请求体
                    .build();
            appRetrofit.getClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("onFailure: ", "上传失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e("onResponse: ", "上传照片成功");
                }
            });
        } else {
            Publish publish = appRetrofit.retrofit().create(Publish.class);
            view.addDisposable(publish.publish(topicTitle, topicContent, typeDiv, topicLabel, contentDiv)
                    .subscribeOn(Schedulers.io())
                    .map(responseBody -> {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        return jsonObject.getInt("resultCode") == 200;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    view.stateSuccess();
                                } else {
                                    view.stateError();
                                }
                            }
                            , throwable -> {
                                throwable.printStackTrace();
                                view.stateNetError();
                            })
            );
        }

    }
}
