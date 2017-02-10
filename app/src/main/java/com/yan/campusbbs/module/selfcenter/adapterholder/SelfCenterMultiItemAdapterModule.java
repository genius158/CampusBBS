package com.yan.campusbbs.module.selfcenter.adapterholder;

import android.content.Context;

import com.yan.campusbbs.module.selfcenter.SelfCenterPresenter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link SelfCenterPresenter}.
 */
@Module
public class SelfCenterMultiItemAdapterModule {

    private final List<DataMultiItem> dataMultiItems;

    public SelfCenterMultiItemAdapterModule(List<DataMultiItem> dataMultiItems ) {
        this.dataMultiItems = dataMultiItems;
    }

    @Provides
    SelfCenterMultiItemAdapter provideSelfCenterMultiItemAdapter(Context context) {
        return new SelfCenterMultiItemAdapter(dataMultiItems,context);
    }
}
