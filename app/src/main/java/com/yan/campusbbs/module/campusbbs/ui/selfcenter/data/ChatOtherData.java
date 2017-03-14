package com.yan.campusbbs.module.campusbbs.ui.selfcenter.data;

import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/3/13.
 */

public class ChatOtherData extends DataMultiItem<Object> {
    public ChatOtherData(Object dataObj) {
        super(ChatAdapter.ITEM_TYPE_CHAT_OTHER, dataObj);
    }
}