package com.yan.campusbbs.module.selfcenter;


import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yan on 2017/2/7.
 */
@Module
public class SelfCenterModule {
    private final SelfCenterContract.View mView;

    public SelfCenterModule(SelfCenterContract.View view) {
        this.mView = view;
    }

    @Provides
    SelfCenterPresenter getSelfCenterPresenter(Context context) {
        return new SelfCenterPresenter(context, mView);
    }
}
