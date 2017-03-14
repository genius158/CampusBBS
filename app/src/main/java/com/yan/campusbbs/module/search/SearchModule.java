package com.yan.campusbbs.module.search;

import android.content.Context;

import com.yan.campusbbs.module.search.adapter.SearchAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link SearchPresenter}.
 */
@Module
public class SearchModule {

    private final SearchContract.View mView;
    List<DataMultiItem> dataMultiItems;

    public SearchModule(SearchContract.View view) {
        mView = view;
        dataMultiItems = new ArrayList<>();
    }

    @Provides
    SearchPresenter provideSearchPresenter(Context context) {
        return new SearchPresenter(context, mView);
    }

    @Provides
    List<DataMultiItem> getDataMultiItems() {
        return dataMultiItems;
    }

    @Provides
    SearchAdapter getSearchAdapter(Context context, RxBus rxBus) {
        return new SearchAdapter(dataMultiItems, context, rxBus);
    }
}
