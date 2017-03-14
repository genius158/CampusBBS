package com.yan.campusbbs.module.search.adapter;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.adapter.CampusDataAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/3/13.
 */

public class SearchAdapter extends CampusDataAdapter {

    @Inject
    public SearchAdapter(List<DataMultiItem> data, Context context) {
        super(data, context);
    }
}
