package com.yan.campusbbs.module.campusbbs.life;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link LifePresenter}.
 */
@Module
public class LifePresenterModule {

    private final LifeContract.View mView;

    public LifePresenterModule(LifeContract.View view) {
        mView = view;
    }

    @Provides
    LifeContract.View provideLifeContractView() {
        return mView;
    }

}
