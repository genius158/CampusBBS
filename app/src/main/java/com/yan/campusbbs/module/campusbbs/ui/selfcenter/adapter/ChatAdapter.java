package com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.R;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.List;

/**
 * Created by yan on 2017/3/13.
 */

public class ChatAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {

    public static final int ITEM_TYPE_CHAT_SELF = 1;
    public static final int ITEM_TYPE_CHAT_OTHER = 2;


    public ChatAdapter(List<DataMultiItem> data, Context context) {
        super(data);
        addItemType(ITEM_TYPE_CHAT_SELF, R.layout.activity_bbs_self_item_chat_self);
        addItemType(ITEM_TYPE_CHAT_OTHER, R.layout.activity_bbs_self_item_chat_other);
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem item) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_CHAT_SELF:
                break;
            case ITEM_TYPE_CHAT_OTHER:
                break;
        }
    }
}
