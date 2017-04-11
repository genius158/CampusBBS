package com.yan.campusbbs.module.campusbbs.ui.selfcenter.message;

import android.content.Context;

import javax.inject.Inject;

public class MessagePresenter implements MessageContract.Presenter {
    private MessageContract.View view;
    private Context context;

    @Inject
    public MessagePresenter(Context context, MessageContract.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }
}
