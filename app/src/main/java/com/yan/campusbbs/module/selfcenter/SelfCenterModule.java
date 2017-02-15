package com.yan.campusbbs.module.selfcenter;


import android.content.Context;

import com.yan.campusbbs.repository.DataMultiItem;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yan on 2017/2/7.
 */
@Module
public class SelfCenterModule {
    private final SelfCenterContract.View mView;
    private final List<DataMultiItem> dataMultiItems;

    public SelfCenterModule(SelfCenterContract.View view) {
        this.mView = view;
        dataMultiItems = new ArrayList<>();
    }

    @Provides
    SelfCenterPresenter getSelfCenterPresenter(Context context) {
        return new SelfCenterPresenter(context, mView);
    }

    @Provides
    List<DataMultiItem> getDataMultiItems() {
        return dataMultiItems;
    }

    @Provides
    SelfCenterMultiItemAdapter getSelfCenterMultiItemAdapter(Context context) {
        return new SelfCenterMultiItemAdapter(dataMultiItems, context);
    }
}
