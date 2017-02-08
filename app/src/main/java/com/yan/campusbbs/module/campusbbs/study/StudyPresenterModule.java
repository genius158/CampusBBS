package com.yan.campusbbs.module.campusbbs.study;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link StudyPresenter}.
 */
@Module
public class StudyPresenterModule {

    private final StudyContract.View mView;

    public StudyPresenterModule(StudyContract.View view) {
        mView = view;
    }

    @Provides
    StudyContract.View provideStudyContractView() {
        return mView;
    }

}
