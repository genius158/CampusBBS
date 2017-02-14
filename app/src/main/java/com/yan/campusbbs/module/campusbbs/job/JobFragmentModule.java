package com.yan.campusbbs.module.campusbbs.job;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.CampusPagerTabAdapter;
import com.yan.campusbbs.util.AnimationHelper;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class JobFragmentModule {
    private JobContract.View view;

    public JobFragmentModule(JobContract.View view) {
        this.view = view;
    }

    @Provides
    JobPresenter getJobPresenter(Context context) {
        return new JobPresenter(context, view);
    }

}
