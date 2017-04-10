package com.yan.campusbbs.module.campusbbs.data;

import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class SelfCenterChatCacheData implements Serializable{
    List<DataMultiItem> chatData;

    public SelfCenterChatCacheData(List<DataMultiItem> chatData) {
        this.chatData = chatData;
    }

    public List<DataMultiItem> getChatData() {
        return chatData;
    }

    public void setChatData(List<DataMultiItem> chatData) {
        this.chatData = chatData;
    }
}
