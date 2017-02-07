package com.yan.campusbbs.module.selfcenter;

import android.content.Context;

import javax.inject.Inject;

public final class SelfCenterPresenter implements SelfCenterContract.Presenter {
    private SelfCenterContract.View view;
    private Context context;

    @Inject
    SelfCenterPresenter(Context context, SelfCenterContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
