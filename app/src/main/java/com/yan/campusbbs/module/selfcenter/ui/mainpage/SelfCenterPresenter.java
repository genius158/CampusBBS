package com.yan.campusbbs.module.selfcenter.ui.mainpage;

import android.content.Context;

import com.yan.campusbbs.module.selfcenter.api.MainPage;
import com.yan.campusbbs.util.AppRetrofit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class SelfCenterPresenter implements SelfCenterContract.Presenter {
    private SelfCenterContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    SelfCenterPresenter(Context context, SelfCenterContract.View view,AppRetrofit appRetrofit) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
    }


    @Override
    public void start() {

    }

    @Override
    public void getMainPageData(int pageNo) {
        MainPage mainPage= appRetrofit.retrofit().create(MainPage.class);
        view.addDisposable(mainPage.getMainPageData(String.valueOf(pageNo))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseBody -> view.setData(responseBody),
               throwable -> {
                   throwable.printStackTrace();
                   view.dataError();
               }));
    }
}
