package com.yan.campusbbs.module.campusbbs;

import android.content.Context;

import com.yan.campusbbs.util.RxBus;

import java.util.List;


import dagger.Module;
import dagger.Provides;

/**
 * Created by yan on 2017/2/7.
 */
@Module
public class PagerTabAdapterModule {

    private final List data;

    public PagerTabAdapterModule(List data) {
        this.data = data;
    }

    @Provides
    PagerTabAdapter provideStudyContractView(Context context) {
        return new PagerTabAdapter(data, context);
    }

}
