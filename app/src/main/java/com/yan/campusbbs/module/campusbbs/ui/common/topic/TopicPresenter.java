package com.yan.campusbbs.module.campusbbs.ui.common.topic;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.api.Topic;
import com.yan.campusbbs.module.campusbbs.data.BannerImgs;
import com.yan.campusbbs.module.campusbbs.data.PostAll;
import com.yan.campusbbs.module.campusbbs.data.PostTag;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.utils.AppRetrofit;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TopicPresenter implements TopicContract.Presenter {
    private TopicContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
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
        {
            view.addDisposable(Observable.zip(search.getTopicHots(typeDiv)
                    , search.getTopicList(pageNum, typeDiv)
                    , (hotData, topicData) -> {
                        List<DataMultiItem> dataMultiItems = new ArrayList<>();
                        if (pageNum.equals("1")) {
                            List<TopicData.DataBean.TopicInfoListBean.TopicListBean> bannerImgs = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                if (hotData.getData().getTopicInfoList().getTopicList().size() > i) {
                                    bannerImgs.add(hotData.getData().getTopicInfoList().getTopicList().get(i));
                                }
                            }
                            dataMultiItems.add(new BannerImgs(bannerImgs));
                        }
                        for (int i = 0; i < topicData.getData().getTopicInfoList().getTopicList().size(); i++) {
                            dataMultiItems.add(new PostAll(topicData.getData().getTopicInfoList().getTopicList().get(i)));
                        }
                        return dataMultiItems;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(dataMultiItems -> {
                        view.setTopicData(dataMultiItems);
                    }, throwable -> {
                        view.netError();
                        throwable.printStackTrace();
                    }));
        }
    }
}
