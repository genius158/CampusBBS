package com.yan.campusbbs.module.search.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.adapter.CampusDataAdapter;
import com.yan.campusbbs.module.search.data.SearchData;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/3/13.
 */

public class SearchAdapter extends CampusDataAdapter {
    private Context context;
    public static final int ITEM_TYPE_SEARCH = 11;

    @Inject
    public SearchAdapter(List<DataMultiItem> data, Context context) {
        super(data, context);
        addItemType(ITEM_TYPE_SEARCH, R.layout.activity_bbs_search_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem multiItem) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_SEARCH:
                holder.setText(R.id.tv_search_text, ((SearchData) multiItem).data);
                break;
        }
    }
}
