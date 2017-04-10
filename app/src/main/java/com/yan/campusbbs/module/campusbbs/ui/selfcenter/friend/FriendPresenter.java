package com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend;

import android.content.Context;

import javax.inject.Inject;

public class FriendPresenter implements FriendContract.Presenter {
    private FriendContract.View view;
    private Context context;

    @Inject
    public FriendPresenter(Context context, FriendContract.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }
}
