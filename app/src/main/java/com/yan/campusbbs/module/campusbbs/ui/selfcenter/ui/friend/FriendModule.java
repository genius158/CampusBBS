package com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.friend;


import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.FriendAdapter;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.FriendData;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class FriendModule {
    private FriendContract.View view;
    private List<FriendData> friendDatas;

    public FriendModule(FriendContract.View view) {
        this.view = view;
        friendDatas = new ArrayList<>();
    }

    @Provides
    FriendContract.Presenter getStudyPresenter(Context context) {
        return new FriendPresenter(context, view);
    }

    @Provides
    FriendAdapter getDataAdapter(Context context) {
        return new FriendAdapter(context,friendDatas);
    }

    @Provides
    List<FriendData> getFriendDatas() {
        return friendDatas;
    }

}
