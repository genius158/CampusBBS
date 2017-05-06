package com.yan.campusbbs.module.campusbbs.ui.selfcenter;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.api.Publish;
import com.yan.campusbbs.module.campusbbs.api.Topic;
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
    public void getSelfPublish() {
        Topic publish = appRetrofit.retrofit().create(Topic.class);
        view.addDisposable(publish.getTopicUserHots()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(publishData -> {
                            view.stateSuccess(publishData);
                        },
                        Throwable::printStackTrace));

    }
}
