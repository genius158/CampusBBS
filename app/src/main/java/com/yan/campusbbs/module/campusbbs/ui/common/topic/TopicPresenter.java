package com.yan.campusbbs.module.campusbbs.ui.common.topic;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.api.Topic;
import com.yan.campusbbs.util.AppRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TopicPresenter implements TopicContract.Presenter {
    private TopicContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    public TopicPresenter(Context context, TopicContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.appRetrofit = appRetrofit;
        this.context = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void getTopicList(String pageNum, int typeDiv, String topicLabel) {
        Topic search = appRetrofit.retrofit().create(Topic.class);
        view.addDisposable(search.getTopicList(pageNum, typeDiv, topicLabel.trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicData -> {
                    view.setTopicTagData(topicData);
                }, throwable -> {
                    view.netError();
                    throwable.printStackTrace();
                })
        );

    }

    @Override
    public void getTopicList(String pageNum, int typeDiv) {
        Topic search = appRetrofit.retrofit().create(Topic.class);
        view.addDisposable(search.getTopicList(pageNum, typeDiv)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicData -> {
                    view.setTopicData(topicData);
                }, throwable -> {
                    view.netError();
                    throwable.printStackTrace();
                })
        );
    }
}
