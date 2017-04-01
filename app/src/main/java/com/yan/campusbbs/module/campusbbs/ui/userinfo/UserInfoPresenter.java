package com.yan.campusbbs.module.campusbbs.ui.userinfo;

import android.content.Context;

import com.yan.campusbbs.util.AppRetrofit;

import javax.inject.Inject;

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
}
