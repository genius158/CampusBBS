package com.yan.campusbbs.module.search.data;

import com.yan.campusbbs.module.search.adapter.SearchAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/3/14.
 */

public class SearchData extends DataMultiItem<String> {
    public SearchData(String dataObj) {
        super(SearchAdapter.ITEM_TYPE_SEARCH, dataObj);
    }
}
