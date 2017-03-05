package com.yan.campusbbs.module.campusbbs.ui.life;

import android.content.Context;

import javax.inject.Inject;

public final class LifePresenter implements LifeContract.Presenter {
    private LifeContract.View view;
    private Context context;

    @Inject
    LifePresenter(Context context, LifeContract.View view) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void start() {

    }
}
