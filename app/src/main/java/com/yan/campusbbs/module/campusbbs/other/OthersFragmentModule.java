package com.yan.campusbbs.module.campusbbs.other;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class OthersFragmentModule {
    private List<OthersAdapter.OthersItem> othersItems;

    public OthersFragmentModule() {
        othersItems = new ArrayList<>();
    }

    @Provides
    List<OthersAdapter.OthersItem> provideOthersItems() {
        return othersItems;
    }

    @Provides
    OthersAdapter provideOthersAdapter(Context context) {
        return new OthersAdapter(othersItems, context);
    }

}
