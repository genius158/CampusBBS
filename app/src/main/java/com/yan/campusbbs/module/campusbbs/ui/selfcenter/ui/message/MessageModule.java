package com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.message;


import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.MessageAdapter;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.MessageData;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {
    private MessageContract.View view;
    private List<MessageData> messageDatas;

    public MessageModule(MessageContract.View view) {
        this.view = view;
        messageDatas = new ArrayList<>();
    }

    @Provides
    MessageContract.Presenter getStudyPresenter(Context context) {
        return new MessagePresenter(context, view);
    }

    @Provides
    MessageAdapter getMessageAdapter(Context context) {
        return new MessageAdapter(context,messageDatas);
    }

    @Provides
    List<MessageData> getMessageDatas() {
        return messageDatas;
    }

}
