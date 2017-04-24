package com.yan.campusbbs.module.campusbbs.ui.common.topicdetail;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.api.Topic;
import com.yan.campusbbs.util.AppRetrofit;

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
        Topic topic = appRetrofit.retrofit().create(Topic.class);
        view.addDisposable(topic.getTopicDetail(topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicDetailData -> {
                    view.setTopicDetail(topicDetailData);
                }, throwable -> {
                    throwable.printStackTrace();
                    view.netError();
                })
        );
    }

    @Override
    public void getReplyList(String topicId, String pageNum) {
        Topic topic = appRetrofit.retrofit().create(Topic.class);
        view.addDisposable(topic.getReplyList(topicId, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicDetailData -> {
                    view.setReplyList(topicDetailData);
                }, throwable -> {
                    throwable.printStackTrace();
                    view.netError();
                })
        );
    }

    @Override
    public void topicLike(String topicId, String likeCount) {
        Topic topic = appRetrofit.retrofit().create(Topic.class);
        view.addDisposable(topic.topicLike(topicId, likeCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicLikeData -> {
                    view.topicLike(topicLikeData);
                }, throwable -> {
                    throwable.printStackTrace();
                    view.netError();
                })
        );
    }

    @Override
    public void replyComment(String topicId, String content) {
        Topic topic = appRetrofit.retrofit().create(Topic.class);
        view.addDisposable(topic.replyComment(topicId, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(replyCommentData -> {
                    view.reply(replyCommentData);
                }, throwable -> {
                    throwable.printStackTrace();
                    view.netError();
                })
        );
    }
}
