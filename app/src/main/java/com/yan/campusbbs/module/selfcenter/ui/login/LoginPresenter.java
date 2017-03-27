package com.yan.campusbbs.module.selfcenter.ui.login;

import android.content.Context;

import javax.inject.Inject;

public final class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private Context context;

    @Inject
    LoginPresenter(Context context, LoginContract.View view) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void start() {

    }
}
