package com.yan.campusbbs.module.selfcenter;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link SelfCenterPresenter}.
 */
@Module
public class SelfCenterPresenterModule {

    private final SelfCenterContract.View mView;

    public SelfCenterPresenterModule(SelfCenterContract.View view ) {
        mView = view;
    }

    @Provides
    SelfCenterContract.View provideSelfCenterContractView() {
        return mView;
    }

}
