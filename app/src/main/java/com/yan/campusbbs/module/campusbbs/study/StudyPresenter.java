package com.yan.campusbbs.module.campusbbs.study;

import android.content.Context;

import javax.inject.Inject;

public final class StudyPresenter implements StudyContract.Presenter {
    private StudyContract.View view;
    private Context context;

    @Inject
    StudyPresenter(Context context, StudyContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
