package com.yan.campusbbs.module.campusbbs.ui.selfcenter.message;


import android.content.Context;


import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterMessageAdapter;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterMessageData;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.message.MessageContract;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.message.MessagePresenter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {
    private MessageContract.View view;
    private List<SelfCenterMessageData> messageDatas;

    public MessageModule(MessageContract.View view) {
        this.view = view;
        messageDatas = new ArrayList<>();
    }

    @Provides
    MessageContract.Presenter getStudyPresenter(Context context) {
        return new MessagePresenter(context, view);
    }

    @Provides
    SelfCenterMessageAdapter getMessageAdapter(Context context) {
        return new SelfCenterMessageAdapter(context,messageDatas);
    }

    @Provides
    List<SelfCenterMessageData> getMessageDatas() {
        return messageDatas;
    }

}
