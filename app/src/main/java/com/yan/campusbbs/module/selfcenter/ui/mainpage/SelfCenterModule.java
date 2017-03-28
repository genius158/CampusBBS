package com.yan.campusbbs.module.selfcenter.ui.mainpage;


import android.content.Context;

import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.AppRetrofit;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yan on 2017/2/7.
 */
@Module
public class SelfCenterModule {
    private final SelfCenterContract.View mView;
    private final List<DataMultiItem> dataMultiItems;

    public SelfCenterModule(SelfCenterContract.View view) {
        this.mView = view;
        dataMultiItems = new ArrayList<>();
    }

    @Provides
    SelfCenterPresenter getSelfCenterPresenter(Context context, AppRetrofit appRetrofit) {
        return new SelfCenterPresenter(context, mView,appRetrofit);
    }

    @Provides
    List<DataMultiItem> getDataMultiItems() {
        return dataMultiItems;
    }

    @Provides
    SelfCenterMultiItemAdapter getSelfCenterMultiItemAdapter(Context context) {
        return new SelfCenterMultiItemAdapter(dataMultiItems, context);
    }
}
