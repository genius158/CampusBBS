package com.yan.campusbbs.module.campusbbs.life;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.CampusPagerTabAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class LifeFragmentModule  {
    private LifeContract.View view;

    private final List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems;

    public LifeFragmentModule(LifeContract.View view) {
        this.view = view;
        this.pagerTabItems = new ArrayList<>();
    }

    @Provides
    CampusPagerTabAdapter provideStudyContractView(Context context) {
        return new CampusPagerTabAdapter(pagerTabItems, context);
    }

    @Provides
    List<CampusPagerTabAdapter.PagerTabItem> getPagerTabItems() {
        return pagerTabItems;
    }

    @Provides
    LifePresenter getLifePresenter(Context context) {
        return new LifePresenter(context, view);
    }

}
