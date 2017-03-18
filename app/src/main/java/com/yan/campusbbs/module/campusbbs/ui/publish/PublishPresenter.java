package com.yan.campusbbs.module.campusbbs.ui.publish;

import android.content.Context;

import javax.inject.Inject;

public class PublishPresenter implements PublishContract.Presenter {
    private PublishContract.View view;
    private Context context;

    @Inject
    public PublishPresenter(Context context, PublishContract.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }
}
