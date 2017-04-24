package com.yan.campusbbs.module.campusbbs.ui.common.topicdetail;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.api.Topic;
import com.yan.campusbbs.util.AppRetrofit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function4;
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

//        view.addDisposable(Observable.zip(
//                topic.getReplyList(topicId, "1")
//                , topic.getReplyList(topicId, "2")
//                , topic.getReplyList(topicId, "3")
//                , topic.getReplyList(topicId, "4")
//                , (topic1, topic2, topic3, topic4) -> {
//                    if (topic1.getData() != null
//                            && topic1.getData().getCommentInfoList() != null
//                            && topic1.getData().getCommentInfoList().getCommentList() != null) {
//                        topic1.getData().getCommentInfoList().getCommentList()
//                                .addAll(topic2.getData().getCommentInfoList().getCommentList());
//                        topic1.getData().getCommentInfoList().getCommentList()
//                                .addAll(topic3.getData().getCommentInfoList().getCommentList());
//                        topic1.getData().getCommentInfoList().getCommentList()
//                                .addAll(topic4.getData().getCommentInfoList().getCommentList());
//                        return topic1;
//                    }
//                    return null;
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(commentData -> {
//                    view.setReplyList(commentData);
//                }, throwable -> {
//                    throwable.printStackTrace();
//                    view.netError();
//                }));

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
