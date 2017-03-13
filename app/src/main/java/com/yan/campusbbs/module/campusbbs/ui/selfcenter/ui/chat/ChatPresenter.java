package com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.chat;

import android.content.Context;

import javax.inject.Inject;

public class ChatPresenter implements ChatContract.Presenter {
    private ChatContract.View view;
    private Context context;

    @Inject
    public ChatPresenter(Context context, ChatContract.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }
}
