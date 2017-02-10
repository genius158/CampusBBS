package com.yan.campusbbs.module.selfcenter;

import android.content.Context;

import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yan on 2017/2/7.
 */
@Module
public class SelfCenterModule {
    private final List<DataMultiItem> multiItems;

    public SelfCenterModule() {
        multiItems = new ArrayList<>();
    }

    @Provides
    SelfCenterMultiItemAdapter provideSelfCenterMultiItemAdapter(Context context) {
        return new SelfCenterMultiItemAdapter(multiItems, context);
    }

    @Provides
    List<DataMultiItem> getMultiItems() {
        return multiItems;
    }
}
