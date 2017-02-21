package com.yan.campusbbs.module.campusbbs.common;


import android.content.Context;

import com.yan.campusbbs.module.selfcenter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class CampusTabPagerModule {
    private List<DataMultiItem> multiItems;
    private final List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems;

    public CampusTabPagerModule() {
        multiItems = new ArrayList<>();
        this.pagerTabItems = new ArrayList<>();
    }

    @Provides
    CampusPagerTabAdapter provideCampusPagerTabAdapter(Context context) {
        return new CampusPagerTabAdapter(pagerTabItems, context);
    }
    @Provides
    List<CampusPagerTabAdapter.PagerTabItem> getPagerTabItems() {
        return pagerTabItems;
    }

    @Provides
    List<DataMultiItem> getMultiItems() {
        return multiItems;
    }

    @Provides
    SelfCenterMultiItemAdapter getSelfCenterMultiItemAdapter(Context context) {
        return new SelfCenterMultiItemAdapter(multiItems, context);
    }
}
