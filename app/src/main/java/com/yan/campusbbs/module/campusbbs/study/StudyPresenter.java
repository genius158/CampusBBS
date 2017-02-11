package com.yan.campusbbs.module.campusbbs.study;

import android.content.Context;

import javax.inject.Inject;

public class StudyPresenter implements StudyContract.Presenter {
    private StudyContract.View view;
    private Context context;

    @Inject
    StudyPresenter(Context context, StudyContract.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }
}
