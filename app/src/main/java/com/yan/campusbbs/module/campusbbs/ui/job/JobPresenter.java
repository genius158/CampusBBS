package com.yan.campusbbs.module.campusbbs.ui.job;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.common.topic.TopicPresenter;
import com.yan.campusbbs.module.campusbbs.ui.study.StudyContract;
import com.yan.campusbbs.util.AppRetrofit;

import javax.inject.Inject;

public final class JobPresenter extends TopicPresenter implements StudyContract.Presenter {
    private JobContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    JobPresenter(Context context, JobContract.View view, AppRetrofit appRetrofit) {
        super(context, view, appRetrofit);
        this.view = view;
        this.appRetrofit = appRetrofit;
        this.context = context;
    }


    @Override
    public void start() {

    }
}
