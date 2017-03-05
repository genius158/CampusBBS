package com.yan.campusbbs.module.campusbbs.ui.job;

import android.content.Context;

import javax.inject.Inject;

public final class JobPresenter implements JobContract.Presenter {
    private JobContract.View view;
    private Context context;

    @Inject
    JobPresenter(Context context, JobContract.View view) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void start() {

    }
}
