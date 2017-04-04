package com.yan.campusbbs.module.selfcenter.ui.register;


import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.AppRetrofit;
import com.yan.campusbbs.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterModule {
    private RegisterContract.View view;

    public RegisterModule(RegisterContract.View view) {
        this.view = view;
    }

    @Provides
    RegisterPresenter getPresenter(Context context, AppRetrofit appRetrofit, ToastUtils toastUtils) {
        return new RegisterPresenter(context, view, appRetrofit, toastUtils);
    }

}
