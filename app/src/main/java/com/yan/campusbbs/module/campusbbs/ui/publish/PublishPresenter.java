package com.yan.campusbbs.module.campusbbs.ui.publish;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.module.campusbbs.api.Publish;
import com.yan.campusbbs.module.campusbbs.data.PublishBackData;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.utils.AppRetrofit;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
        File file = null;
        if (!TextUtils.isEmpty(filePath)) {
            file = new File(filePath);
        }
        Log.e("publish: ", "publish");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (file != null
                && file.exists()) {
            Log.e("publish: ", "file.exists");
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        //添加其它信息
        builder.addFormDataPart("topicTitle", topicTitle);
        builder.addFormDataPart("topicContent", topicContent);
        builder.addFormDataPart("typeDiv", typeDiv);
        builder.addFormDataPart("topicLabel", topicLabel);
//        builder.addFormDataPart("contentDiv", contentDiv);
        builder.addFormDataPart("fileTypeDiv", "1");

        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(DataAddress.MAIN_PATH + DataAddress.URL_PUBLISH
                        +"?topicTitle="+topicTitle
                        +"&topicContent="+topicContent
                        +"&typeDiv="+typeDiv
                        +"&topicLabel="+topicLabel
                        +"&fileTypeDiv="+"1"
                )
                .post(requestBody)
                .build();

        view.addDisposable(Observable.create((ObservableEmitter<String> e) -> {
            Response response = appRetrofit.getClient().newCall(request).execute();
            e.onNext(response.body().string());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(str -> {
                    Log.e("publish: ", str);
                    PublishBackData publishBackData=new Gson().fromJson(str,PublishBackData.class);
                    if (publishBackData.getResultCode()==200){
                        view.stateSuccess();
                    }else {
                        view.stateError(publishBackData.getMessage());
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                    view.stateNetError();
                }));

    }
}
