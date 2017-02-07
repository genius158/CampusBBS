package com.yan.campusbbs.module.campusbbs;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link CampusBBSPresenter}.
 */
@Module
public class CampusBBSPresenterModule {

    private final CampusBBSContract.View mView;

    public CampusBBSPresenterModule(CampusBBSContract.View view ) {
        mView = view;
    }

    @Provides
    CampusBBSContract.View provideSelfCenterContractView() {
        return mView;
    }

}
