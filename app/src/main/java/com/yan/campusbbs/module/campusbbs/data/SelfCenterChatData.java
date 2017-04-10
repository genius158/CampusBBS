package com.yan.campusbbs.module.campusbbs.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class SelfCenterChatData implements Serializable {
    public String head;
    public String text;
    public String identifier;
    public long time;

    public SelfCenterChatData(String head, String text, long time) {
        this.head = head;
        this.text = text;
        this.time = time;
    }

    public SelfCenterChatData(String identifier, String header, String text, long time) {
        this.head = header;
        this.identifier = identifier;
        this.text = text;
        this.time = time;
    }
}
