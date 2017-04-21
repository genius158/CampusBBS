package com.yan.campusbbs.module.campusbbs.ui.common.topicdetail;


import android.content.Context;

import com.yan.campusbbs.util.AppRetrofit;

import dagger.Module;
import dagger.Provides;

@Module
public class TopicDetailModule {
    private TopicDetailContract.View view;

    public TopicDetailModule(TopicDetailContract.View view) {
        this.view = view;
    }

    @Provides
    TopicDetailPresenter getPresenter(Context context, AppRetrofit appRetrofit) {
        return new TopicDetailPresenter(context, view, appRetrofit);
    }

}
