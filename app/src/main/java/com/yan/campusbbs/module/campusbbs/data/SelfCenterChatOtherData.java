package com.yan.campusbbs.module.campusbbs.data;


//import com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter.ChatAdapter;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterChatAdapter;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterChatOtherData extends DataMultiItem<Object> implements Serializable{
    public SelfCenterChatOtherData(Object dataObj) {
        super(SelfCenterChatAdapter.ITEM_TYPE_CHAT_OTHER, dataObj);
    }
}