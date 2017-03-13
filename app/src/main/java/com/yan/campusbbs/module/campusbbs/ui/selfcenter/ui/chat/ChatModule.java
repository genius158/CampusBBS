package com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.chat;


import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatModule {
    private ChatContract.View view;
    private List<DataMultiItem> dataMultiItems;

    public ChatModule(ChatContract.View view) {
        this.view = view;
        dataMultiItems = new ArrayList<>();
    }

    @Provides
    ChatContract.Presenter getStudyPresenter(Context context) {
        return new ChatPresenter(context, view);
    }

    @Provides
    ChatAdapter getChatAdapter(Context context) {
        return new ChatAdapter(dataMultiItems,context);
    }

    @Provides
    List<DataMultiItem> getDataMultiItems() {
        return dataMultiItems;
    }

}
