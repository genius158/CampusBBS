package com.yan.campusbbs.module.campusbbs.ui.selfcenter;


import android.content.Context;

import com.yan.campusbbs.util.AppRetrofit;

import dagger.Module;
import dagger.Provides;

@Module
public class SelfCenterModule {
    private SelfCenterContract.View view;

    public SelfCenterModule(SelfCenterContract.View view) {
        this.view = view;
    }

    @Provides
    SelfCenterPresenter getPresenter(Context context, AppRetrofit appRetrofit) {
        return new SelfCenterPresenter(context, view,appRetrofit);
    }

}
