package com.yan.campusbbs.module.campusbbs.ui.common;


import android.content.Context;

import com.yan.campusbbs.module.campusbbs.adapter.CampusDataAdapter;
import com.yan.campusbbs.module.campusbbs.adapter.CampusPagerTabAdapter;
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
    CampusDataAdapter getCampusDataAdapter(Context context) {
        return new CampusDataAdapter(multiItems, context);
    }
}
