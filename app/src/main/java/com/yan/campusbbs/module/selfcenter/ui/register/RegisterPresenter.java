package com.yan.campusbbs.module.selfcenter.ui.register;

import android.content.Context;

import javax.inject.Inject;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private Context context;

    @Inject
    public RegisterPresenter(Context context, RegisterContract.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }
}
