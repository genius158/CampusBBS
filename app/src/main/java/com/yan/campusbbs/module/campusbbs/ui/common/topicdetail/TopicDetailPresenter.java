package com.yan.campusbbs.module.campusbbs.ui.common.topicdetail;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.api.Publish;
import com.yan.campusbbs.util.AppRetrofit;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TopicDetailPresenter implements TopicDetailContract.Presenter {
    private TopicDetailContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    public TopicDetailPresenter(Context context, TopicDetailContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
    }

    @Override
    public void start() {

    }

    @Override
    public void getTopicDetail(String topicId) {

    }
}
