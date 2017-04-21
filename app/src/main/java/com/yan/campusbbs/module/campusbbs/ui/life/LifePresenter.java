package com.yan.campusbbs.module.campusbbs.ui.life;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.common.topic.TopicPresenter;
import com.yan.campusbbs.module.campusbbs.ui.study.StudyContract;
import com.yan.campusbbs.util.AppRetrofit;

import javax.inject.Inject;

public final class LifePresenter extends TopicPresenter implements StudyContract.Presenter {
    private LifeContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    LifePresenter(Context context, LifeContract.View view, AppRetrofit appRetrofit) {
        super(context, view, appRetrofit);
        this.view = view;
        this.appRetrofit = appRetrofit;
        this.context = context;
    }


    @Override
    public void start() {

    }
}
