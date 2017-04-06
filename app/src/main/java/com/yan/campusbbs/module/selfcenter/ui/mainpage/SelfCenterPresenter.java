package com.yan.campusbbs.module.selfcenter.ui.mainpage;

import android.content.Context;

import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.selfcenter.action.LogInAction;
import com.yan.campusbbs.module.selfcenter.api.MainPage;
import com.yan.campusbbs.module.selfcenter.data.FriendDynamic;
import com.yan.campusbbs.module.selfcenter.data.FriendTitle;
import com.yan.campusbbs.module.selfcenter.data.MainPageData;
import com.yan.campusbbs.module.selfcenter.data.OtherCenterHeader;
import com.yan.campusbbs.module.selfcenter.data.SelfCenterHeader;
import com.yan.campusbbs.module.selfcenter.data.SelfDynamic;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.AppRetrofit;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class SelfCenterPresenter implements SelfCenterContract.Presenter {
    private SelfCenterContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;
    private ToastUtils toastUtils;
    private SPUtils spUtils;
    private RxBus rxBus;

    @Inject
    SelfCenterPresenter(Context context, SelfCenterContract.View view, AppRetrofit appRetrofit, ToastUtils toastUtils, SPUtils spUtils, RxBus rxBus) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
        this.toastUtils = toastUtils;
        this.spUtils = spUtils;
        this.rxBus = rxBus;
    }

    @Override
    public void start() {

    }

    @Override
    public void getMainPageData(int pageNo) {
        MainPage mainPage = appRetrofit.retrofit().create(MainPage.class);
        view.addDisposable(Observable.zip(mainPage.getMainPageSelfData()
                , mainPage.getMainPageData(String.valueOf(pageNo))
                , (mainPageData, othersData) -> {
                    List<DataMultiItem> dataMultiItems = new ArrayList<>();
                    if (mainPageData.getResultCode() != 200
                            && othersData.getResultCode() != 200) {
                        view.addDisposable(toastUtils.showUIShort(mainPageData.getMessage()));
                    } else {
                        if (mainPageData.getData().getTopicInfoList() != null
                                && mainPageData.getData().getTopicInfoList().getTopicList() != null) {
                            for (MainPageData.DataBean.TopicInfoListBean.TopicListBean bean : mainPageData.getData().getTopicInfoList().getTopicList()) {
                                dataMultiItems.add(new SelfDynamic(bean));
                            }
                        }
                        dataMultiItems.add(new FriendTitle());
                        if (othersData.getData().getTopicInfoList() != null
                                && othersData.getData().getTopicInfoList().getTopicList() != null) {
                            for (MainPageData.DataBean.TopicInfoListBean.TopicListBean bean : othersData.getData().getTopicInfoList().getTopicList()) {
                                dataMultiItems.add(new FriendDynamic(bean));
                            }
                        }
                    }

                    if (mainPageData.getResultCode() == 401) {
                        spUtils.putString(Context.MODE_PRIVATE
                                , SharedPreferenceConfig.SHARED_PREFERENCE
                                , SharedPreferenceConfig.SESSION_ID
                                , "");
                        rxBus.post(new LogInAction(false));
                    }
                    return dataMultiItems;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataMultiItems -> {
                    view.dataSuccess(dataMultiItems);
                }, throwable -> {
                    view.dataError();
                    throwable.printStackTrace();
                }));
    }

    @Override
    public void getFriendData(int pageNo, String userId) {
        MainPage mainPage = appRetrofit.retrofit().create(MainPage.class);
        view.addDisposable(mainPage.getMainPageData(String.valueOf(pageNo)).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(mainPageData -> {
            List<DataMultiItem> dataMultiItems = new ArrayList<>();
            dataMultiItems.add(new OtherCenterHeader(ACache.get(context)
                    .getAsObject(CacheConfig.USER_INFO)));

            if (mainPageData.getData().getTopicInfoList() != null
                    && mainPageData.getData().getTopicInfoList().getTopicList() != null) {
                for (MainPageData.DataBean.TopicInfoListBean.TopicListBean bean : mainPageData.getData().getTopicInfoList().getTopicList()) {
                    dataMultiItems.add(new FriendDynamic(bean));
                }
            }
            return dataMultiItems;
        }).subscribe(dataMultiItems -> {
                    view.dataSuccess(dataMultiItems);
                },throwable -> {
                    throwable.printStackTrace();
                    view.dataError();
                }));

    }
}
