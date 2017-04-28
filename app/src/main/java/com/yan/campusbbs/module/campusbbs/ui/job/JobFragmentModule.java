package com.yan.campusbbs.module.campusbbs.ui.job;

import android.content.Context;

import com.yan.campusbbs.utils.AppRetrofit;

import dagger.Module;
import dagger.Provides;

@Module
public class JobFragmentModule {
    private JobContract.View view;

    public JobFragmentModule(JobContract.View view) {
        this.view = view;
    }

    @Provides
    JobPresenter getJobPresenter(Context context, AppRetrofit appRetrofit) {
        return new JobPresenter(context, view,appRetrofit);
    }

}
