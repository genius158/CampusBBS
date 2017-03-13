package com.yan.campusbbs.module.campusbbs.ui.selfcenter.data;

import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/3/13.
 */

public class ChatSelfData extends DataMultiItem {
    public ChatSelfData(Object dataObj) {
        super(ChatAdapter.ITEM_TYPE_CHAT_SELF, dataObj);
    }
}