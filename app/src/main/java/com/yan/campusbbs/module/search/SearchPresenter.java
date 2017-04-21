package com.yan.campusbbs.module.search;

import android.content.Context;

import com.yan.campusbbs.module.search.api.Search;
import com.yan.campusbbs.util.AppRetrofit;

import javax.inject.Inject;

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
    public void getTopicList(String pageNum, String searchKey, int typeDiv) {
        Search search = appRetrofit.retrofit().create(Search.class);
        view.addDisposable(search.getTopicList(pageNum, searchKey, typeDiv)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                }, throwable -> {
                    throwable.printStackTrace();
                })
        );
    }
}
