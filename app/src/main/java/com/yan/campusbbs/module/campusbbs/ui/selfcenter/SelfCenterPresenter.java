package com.yan.campusbbs.module.campusbbs.ui.selfcenter;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.api.Publish;
import com.yan.campusbbs.module.campusbbs.api.UserInfo;
import com.yan.campusbbs.utils.AppRetrofit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelfCenterPresenter implements SelfCenterContract.Presenter {
    private SelfCenterContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    public SelfCenterPresenter(Context context, SelfCenterContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
    }

    @Override
    public void start() {

    }

    @Override
    public void getSelfPublish(int pageNo) {
        Publish publish = appRetrofit.retrofit().create(Publish.class);
        view.addDisposable(publish.getPublish(String.valueOf(pageNo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(publishData -> {
                            view.stateSuccess(publishData);
                        },
                        Throwable::printStackTrace));

    }
}
