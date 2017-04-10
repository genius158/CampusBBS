package com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend;


import android.content.Context;


import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterFriendAdapter;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterFriendData;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class FriendModule {
    private FriendContract.View view;
    private List<SelfCenterFriendData> friendDatas;

    public FriendModule(FriendContract.View view) {
        this.view = view;
        friendDatas = new ArrayList<>();
    }

    @Provides
    FriendContract.Presenter getStudyPresenter(Context context) {
        return new FriendPresenter(context, view);
    }

    @Provides
    SelfCenterFriendAdapter getDataAdapter(Context context) {
        return new SelfCenterFriendAdapter(context,friendDatas);
    }

    @Provides
    List<SelfCenterFriendData> getFriendDatas() {
        return friendDatas;
    }

}
