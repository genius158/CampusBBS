package com.yan.campusbbs.module.campusbbs;

import android.content.Context;

import javax.inject.Inject;

public final class CampusBBSPresenter implements CampusBBSContract.Presenter {
    private CampusBBSContract.View view;
    private Context context;

    @Inject
    CampusBBSPresenter(Context context, CampusBBSContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
