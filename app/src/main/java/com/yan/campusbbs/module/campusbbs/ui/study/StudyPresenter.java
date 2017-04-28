package com.yan.campusbbs.module.campusbbs.ui.study;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.common.topic.TopicPresenter;
import com.yan.campusbbs.utils.AppRetrofit;

import javax.inject.Inject;

public class StudyPresenter extends TopicPresenter implements StudyContract.Presenter {
    private StudyContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    public StudyPresenter(Context context, StudyContract.View view, AppRetrofit appRetrofit) {
        super(context, view, appRetrofit);
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }
}
