package com.yan.campusbbs.module.campusbbs.ui.life;

import android.content.Context;


import dagger.Module;
import dagger.Provides;

@Module
public class LifeFragmentModule  {
    private LifeContract.View view;
    public LifeFragmentModule(LifeContract.View view) {
        this.view = view;
    }
    @Provides
    LifePresenter getLifePresenter(Context context) {
        return new LifePresenter(context, view);
    }

}
