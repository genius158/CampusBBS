package com.yan.campusbbs.module.campusbbs.data;


//import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterChatAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterChatSelfData extends DataMultiItem<Object> {
    public SelfCenterChatSelfData(Object dataObj) {
        super(SelfCenterChatAdapter.ITEM_TYPE_CHAT_SELF, dataObj);
    }
}