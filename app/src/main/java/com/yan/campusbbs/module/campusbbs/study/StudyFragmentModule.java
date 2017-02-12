package com.yan.campusbbs.module.campusbbs.study;


import android.content.Context;

import com.yan.campusbbs.module.campusbbs.CampusPagerTabAdapter;
import com.yan.campusbbs.module.selfcenter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.DataMultiItem;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class StudyFragmentModule {
    private List<DataMultiItem> multiItems;
    private StudyContract.View view;
    private final List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems;

    public StudyFragmentModule(StudyContract.View view) {
        multiItems = new ArrayList<>();
        this.pagerTabItems = new ArrayList<>();
        this.view = view;
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
    List<DataMultiItem> getMultiItems() {
        return multiItems;
    }

    @Provides
    SelfCenterMultiItemAdapter getSelfCenterMultiItemAdapter(Context context) {
        return new SelfCenterMultiItemAdapter(multiItems, context);
    }

    @Provides
    StudyPresenter getStudyPresenter(Context context) {
        return new StudyPresenter(context, view);
    }

}
