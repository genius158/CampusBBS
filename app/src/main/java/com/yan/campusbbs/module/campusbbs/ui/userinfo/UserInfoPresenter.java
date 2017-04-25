package com.yan.campusbbs.module.campusbbs.ui.userinfo;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.api.UserInfo;
import com.yan.campusbbs.util.AppRetrofit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void modify(String nickname, String realName, String headImg, String mood, String email, String age, String gender, String birth, String major, String school, String address) {
        UserInfo userInfo = appRetrofit.retrofit().create(UserInfo.class);
        view.addDisposable(
                userInfo.userInfoEdit(nickname, realName, headImg, mood, email, age, gender, birth, major, school, address)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(logInData -> {
if (logInData.getResultCode()==200){
    view.stateSuccess();
}else {
    view.stateError(logInData.getMessage());
}
                        }, Throwable::printStackTrace)
        );
    }
}
