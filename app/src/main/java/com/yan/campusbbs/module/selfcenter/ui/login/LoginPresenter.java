package com.yan.campusbbs.module.selfcenter.ui.login;

import android.content.Context;

import com.yan.campusbbs.module.selfcenter.api.Login;
import com.yan.campusbbs.util.AppRetrofit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    LoginPresenter(Context context, LoginContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
    }


    @Override
    public void start() {

    }

    @Override
    public void login(String userName, String userPassword) {
        Login login = appRetrofit.retrofit().create(Login.class);
        view.addDisposable(
                login.login(userName, userPassword)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(logInData -> {

                           view.loginSuccess(logInData);
                        }, Throwable::printStackTrace)
        );
    }
}
