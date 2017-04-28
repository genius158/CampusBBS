package com.yan.campusbbs.module.selfcenter.ui.mainpage;


import android.content.Context;
import android.view.View;

import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.common.pop.PopPhotoView;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.utils.AppRetrofit;
import com.yan.campusbbs.utils.RxBus;
import com.yan.campusbbs.utils.SPUtils;
import com.yan.campusbbs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/2/7.
 */
@Module
public class SelfCenterModule {
    private final SelfCenterContract.View mView;
    private final View parent;
    private final List<DataMultiItem> dataMultiItems;
    private CompositeDisposable compositeDisposable;

    public SelfCenterModule(SelfCenterContract.View view, View popParent, CompositeDisposable compositeDisposable) {
        this.mView = view;
        this.parent = popParent;
        this.compositeDisposable = compositeDisposable;
        dataMultiItems = new ArrayList<>();
    }


    @Provides
    SelfCenterPresenter getSelfCenterPresenter(Context context, AppRetrofit appRetrofit, ToastUtils toastUtils, SPUtils spUtils, RxBus rxBus) {
        return new SelfCenterPresenter(context, mView, appRetrofit, toastUtils, spUtils, rxBus);
    }

    @Provides
    List<DataMultiItem> getDataMultiItems() {
        return dataMultiItems;
    }

    @Provides
    SelfCenterMultiItemAdapter getSelfCenterMultiItemAdapter(Context context, ToastUtils toastUtils,RxBus rxBus) {
        SelfCenterMultiItemAdapter selfCenterMultiItemAdapter =
                new SelfCenterMultiItemAdapter(dataMultiItems, context, compositeDisposable,rxBus);
        selfCenterMultiItemAdapter.setPopPhotoView(
                new PopPhotoView(parent, toastUtils));
        return selfCenterMultiItemAdapter;
    }

}
