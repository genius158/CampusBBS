package com.yan.campusbbs.module.campusbbs.ui.publish;


import android.content.Context;

import com.yan.campusbbs.utils.AppRetrofit;


import dagger.Module;
import dagger.Provides;

@Module
public class PublishModule {
    private PublishContract.View view;

    public PublishModule(PublishContract.View view) {
        this.view = view;
    }

    @Provides
    PublishPresenter getPresenter(Context context, AppRetrofit appRetrofit) {
        return new PublishPresenter(context, view,appRetrofit);
    }

}
