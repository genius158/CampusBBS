package com.yan.campusbbs.module.selfcenter.ui.register;

import android.content.Context;

import com.yan.campusbbs.module.selfcenter.api.Register;
import com.yan.campusbbs.util.AppRetrofit;
import com.yan.campusbbs.util.ToastUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private Context context;
    private ToastUtils toastUtils;
    private AppRetrofit appRetrofit;

    @Inject
    public RegisterPresenter(Context context, RegisterContract.View view, AppRetrofit appRetrofit, ToastUtils toastUtils) {
        this.view = view;
        this.context = context;
        this.toastUtils = toastUtils;
        this.appRetrofit = appRetrofit;
    }

    @Override
    public void start() {

    }

    @Override
    public void register(String phoneNum, String password, String nickname, String mood, String email, String school, String birth) {
        Register register = appRetrofit.retrofit().create(Register.class);
        view.addDisposable(register.register(
                phoneNum, password, nickname, mood, email, school, birth
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    view.success();
                }, throwable -> {
                    throwable.printStackTrace();
                    view.netError();
                }));
    }
}
