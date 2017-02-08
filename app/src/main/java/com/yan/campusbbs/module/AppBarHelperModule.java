package com.yan.campusbbs.module;

import android.content.Context;
import android.view.View;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/2/9.
 */

@Module
public class AppBarHelperModule {
    private final View appBar;

    public AppBarHelperModule(View appBar) {
        this.appBar = appBar;
    }

    @Provides
    AppBarHelper provideAppBarHelper(Context context) {
        return new AppBarHelper(context, appBar);
    }
}

