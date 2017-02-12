package com.yan.campusbbs.module.campusbbs.job;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.CampusPagerTabAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class JobFragmentModule {
    private JobContract.View view;
    private final List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems;

    public JobFragmentModule(JobContract.View view) {
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
    JobPresenter getJobPresenter(Context context) {
        return new JobPresenter(context, view);
    }
}
