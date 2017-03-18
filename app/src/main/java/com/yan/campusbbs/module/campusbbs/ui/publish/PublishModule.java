package com.yan.campusbbs.module.campusbbs.ui.publish;


import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class PublishModule {
    private PublishContract.View view;

    public PublishModule(PublishContract.View view) {
        this.view = view;
    }

    @Provides
    PublishContract.Presenter getPresenter(Context context) {
        return new PublishPresenter(context, view);
    }

}
