package com.yan.campusbbs.module.search;

import android.content.Context;

import javax.inject.Inject;

public final class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private Context context;

    @Inject
    SearchPresenter(Context context, SearchContract.View view) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void start() {

    }
}
