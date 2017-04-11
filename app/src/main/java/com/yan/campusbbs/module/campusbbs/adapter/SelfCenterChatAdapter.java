package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterChatData;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterChatAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {

    public static final int ITEM_TYPE_CHAT_SELF = 1;
    public static final int ITEM_TYPE_CHAT_OTHER = 2;

    @Inject
    public SelfCenterChatAdapter(List<DataMultiItem> data, Context context) {
        super(data);
        addItemType(ITEM_TYPE_CHAT_SELF, R.layout.activity_bbs_self_item_chat_self);
        addItemType(ITEM_TYPE_CHAT_OTHER, R.layout.activity_bbs_self_item_chat_other);
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem item) {
        SelfCenterChatData chatData = (SelfCenterChatData) item.data;

        switch (holder.getItemViewType()) {
            case ITEM_TYPE_CHAT_SELF:
                ((SimpleDraweeView) holder.getView(R.id.sdv_head))
                        .setImageURI(chatData.userProfile.faceUrl);
                holder.setText(R.id.tv_text, chatData.text);
                holder.setText(R.id.tv_time, TimeUtils.getTime(chatData.time));
                break;
            case ITEM_TYPE_CHAT_OTHER:
                ((SimpleDraweeView) holder.getView(R.id.sdv_head))
                        .setImageURI(chatData.userProfile.faceUrl);
                holder.setText(R.id.tv_text, chatData.text);
                holder.setText(R.id.tv_time, TimeUtils.getTime(chatData.time));
                break;
        }
    }

}
