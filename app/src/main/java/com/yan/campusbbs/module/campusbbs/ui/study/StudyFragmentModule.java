package com.yan.campusbbs.module.campusbbs.ui.study;


import android.content.Context;

import com.yan.campusbbs.utils.AppRetrofit;

import dagger.Module;
import dagger.Provides;

@Module
public class StudyFragmentModule {
    private StudyContract.View view;

    public StudyFragmentModule(StudyContract.View view) {
        this.view = view;
    }


    @Provides
    StudyPresenter getStudyPresenter(Context context, AppRetrofit appRetrofit) {
        return new StudyPresenter(context, view,appRetrofit);
    }

}
