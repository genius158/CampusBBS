package com.yan.campusbbs.module.campusbbs.job;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link JobPresenter}.
 */
@Module
public class JobPresenterModule {

    private final JobContract.View mView;

    public JobPresenterModule(JobContract.View view) {
        mView = view;
    }

    @Provides
    JobContract.View provideJobContractView() {
        return mView;
    }

}
