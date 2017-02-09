package com.yan.campusbbs.module.campusbbs.life;

import android.content.Context;

import javax.inject.Inject;

public final class LifePresenter implements LifeContract.Presenter {
    private LifeContract.View view;
    private Context context;

    @Inject
    LifePresenter(Context context, LifeContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
