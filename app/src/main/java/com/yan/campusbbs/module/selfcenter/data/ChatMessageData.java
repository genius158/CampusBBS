package com.yan.campusbbs.module.selfcenter.data;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class ChatMessageData {
    public String time;
    public String text;
    public String userId;

    public ChatMessageData(String time, String text, String userId) {
        this.time = time;
        this.text = text;
        this.userId = userId;
    }
}
