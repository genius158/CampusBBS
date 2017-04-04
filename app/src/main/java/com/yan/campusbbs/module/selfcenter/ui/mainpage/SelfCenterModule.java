package com.yan.campusbbs.module.selfcenter.ui.mainpage;


import android.content.Context;
import android.view.View;

import com.yan.campusbbs.module.common.PopPhotoView;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.AppRetrofit;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

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
    private final View parent;
    private final List<DataMultiItem> dataMultiItems;
    private PopPhotoView popPhotoView;

    public SelfCenterModule(SelfCenterContract.View view, View popParent) {
        this.mView = view;
        this.parent = popParent;
        dataMultiItems = new ArrayList<>();
        popPhotoView = new PopPhotoView(popParent);
    }

    @Provides
    SelfCenterPresenter getSelfCenterPresenter(Context context, AppRetrofit appRetrofit, ToastUtils toastUtils, SPUtils spUtils, RxBus rxBus) {
        return new SelfCenterPresenter(context, mView, appRetrofit,toastUtils,spUtils,rxBus);
    }

    @Provides
    List<DataMultiItem> getDataMultiItems() {
        return dataMultiItems;
    }

    @Provides
    SelfCenterMultiItemAdapter getSelfCenterMultiItemAdapter(Context context) {
       SelfCenterMultiItemAdapter selfCenterMultiItemAdapter=
               new SelfCenterMultiItemAdapter(dataMultiItems,context);
        selfCenterMultiItemAdapter.setPopPhotoView(popPhotoView);
        return selfCenterMultiItemAdapter;
    }

    @Provides
    PopPhotoView getPopPhotoView() {
        return popPhotoView;
    }
}
