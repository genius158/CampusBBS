package com.yan.campusbbs.module.campusbbs.ui.userinfo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yan.campusbbs.module.campusbbs.api.UserInfo;
import com.yan.campusbbs.module.campusbbs.data.ModifyData;
import com.yan.campusbbs.module.campusbbs.data.PublishBackData;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.utils.AppRetrofit;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfoPresenter implements UserInfoContract.Presenter {
    private UserInfoContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    public UserInfoPresenter(Context context, UserInfoContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
    }

    @Override
    public void start() {

    }

    @Override
    public void modify(String nickname, String realName, String headImg, String mood, String email, String age, String gender, String birth, String major, String school, String address, String headerFile) {
        File file = null;
        if (!TextUtils.isEmpty(headerFile)) {
            file = new File(headerFile);
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (file != null && file.exists()) {
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }

        builder.addFormDataPart("nickname", nickname);
        builder.addFormDataPart("realName", realName);
        builder.addFormDataPart("mood", mood);
        builder.addFormDataPart("email", email);
        builder.addFormDataPart("age", age);
        builder.addFormDataPart("gender", gender);
        builder.addFormDataPart("birth", birth);
        builder.addFormDataPart("school", school);
        builder.addFormDataPart("address", address);
        builder.addFormDataPart("fileTypeDiv", "1");

        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(DataAddress.MAIN_PATH + DataAddress.URL_USER_EDIT
                        + "?nickname=" + nickname
                        + "&realName=" + realName
                        + "&mood=" + mood
                        + "&email=" + email
                        + "&age=" + age
                        + "&mood=" + mood
                        + "&gender=" + gender
                        + "&birth=" + birth
                        + "&school=" + school
                        + "&address=" + address
                        + "&fileTypeDiv=" + "1"
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
                    ModifyData publishBackData = new Gson().fromJson(str, ModifyData.class);
                    if (publishBackData.getResultCode() == 200) {
                        view.stateSuccess();
                    } else {
                        view.stateError(publishBackData.getMessage());
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                    view.stateNetError();
                }));


    }

    @Override
    public void getSelfInfo() {
        UserInfo userInfo = appRetrofit.retrofit().create(UserInfo.class);
        view.addDisposable(
                userInfo.getSelfInfo()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(infoData -> {
                            if (infoData.getResultCode() == 200) {
                                view.setSelfInfo(infoData);
                            } else {
                                view.stateError(infoData.getMessage());
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            view.stateNetError();
                        })
        );
    }

    @Override
    public void getSelfInfo(String userId) {
        UserInfo userInfo = appRetrofit.retrofit().create(UserInfo.class);
        view.addDisposable(
                userInfo.getUserInfo(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(infoData -> {
                            if (infoData.getResultCode() == 200) {
                                view.setSelfInfo(infoData);
                            } else {
                                view.stateError(infoData.getMessage());
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            view.stateNetError();
                        })
        );
    }
}
