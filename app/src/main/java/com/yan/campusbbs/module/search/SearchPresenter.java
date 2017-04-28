package com.yan.campusbbs.module.search;

import android.content.Context;

import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.campusbbs.data.PostTag;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.module.common.data.VisitorsCacheData;
import com.yan.campusbbs.module.search.api.Search;
import com.yan.campusbbs.module.selfcenter.api.MainPage;
import com.yan.campusbbs.module.selfcenter.data.FriendDynamic;
import com.yan.campusbbs.module.selfcenter.data.OtherCenterHeader;
import com.yan.campusbbs.module.selfcenter.data.PublishData;
import com.yan.campusbbs.module.selfcenter.data.SelfDynamic;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.utils.ACache;
import com.yan.campusbbs.utils.AppRetrofit;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    SearchPresenter(Context context, SearchContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.appRetrofit = appRetrofit;
        this.context = context;
    }


    @Override
    public void start() {

    }

    @Override
    public void getTopicList(String pageNum, String searchKey, int typeDiv, int topicLabel) {
        Search search = appRetrofit.retrofit().create(Search.class);
        view.addDisposable(search.getTopicList(pageNum, searchKey, typeDiv, topicLabel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                }, throwable -> {
                    throwable.printStackTrace();
                })
        );
    }

    @Override
    public void getTopicList(String pageNum, String searchKey ) {
        Search search = appRetrofit.retrofit().create(Search.class);
        view.addDisposable(Observable.zip(search.getTopicList(pageNum, searchKey, 1),
                search.getTopicList(pageNum, searchKey, 2)
                , search.getTopicList(pageNum, searchKey, 3)
                , (topicData, topicData2, topicData3) -> {
                    List<DataMultiItem> dataMultiItems = new ArrayList<>();
                    if (topicData.getData().getTopicInfoList() != null
                            && topicData.getData().getTopicInfoList().getTopicList() != null) {
                        for (TopicData.DataBean.TopicInfoListBean.TopicListBean bean : topicData.getData().getTopicInfoList().getTopicList()) {
                            dataMultiItems.add(new PostTag(bean));
                        }
                    }
                     if (topicData2.getData().getTopicInfoList() != null
                            && topicData2.getData().getTopicInfoList().getTopicList() != null) {
                        for (TopicData.DataBean.TopicInfoListBean.TopicListBean bean : topicData2.getData().getTopicInfoList().getTopicList()) {
                            dataMultiItems.add(new PostTag(bean));
                        }
                    }
                     if (topicData3.getData().getTopicInfoList() != null
                            && topicData3.getData().getTopicInfoList().getTopicList() != null) {
                        for (TopicData.DataBean.TopicInfoListBean.TopicListBean bean : topicData3.getData().getTopicInfoList().getTopicList()) {
                            dataMultiItems.add(new PostTag(bean));
                        }
                    }
                    return dataMultiItems;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataMultiItems -> {
                            view.setTopic(dataMultiItems);
                        }
                        , throwable -> {
                            throwable.printStackTrace();
                            view.netError();
                        }));

    }
}
