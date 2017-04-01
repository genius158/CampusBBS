package com.yan.campusbbs.module.campusbbs.ui.userinfo;


import android.content.Context;

import com.yan.campusbbs.util.AppRetrofit;

import dagger.Module;
import dagger.Provides;

@Module
public class UserInfoModule {
    private UserInfoContract.View view;

    public UserInfoModule(UserInfoContract.View view) {
        this.view = view;
    }

    @Provides
    UserInfoPresenter getPresenter(Context context, AppRetrofit appRetrofit) {
        return new UserInfoPresenter(context, view,appRetrofit);
    }

}
