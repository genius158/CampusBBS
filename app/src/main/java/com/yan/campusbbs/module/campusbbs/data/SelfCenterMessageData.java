package com.yan.campusbbs.module.campusbbs.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterMessageData implements Serializable {
    private String data;
    private String identifier;
    private String nikeName;
    private String userId;
    private String messageNotice;
    private long time;
    private String headUrl;


    public SelfCenterMessageData(String messageNotice, String data) {
        this.data = data;
        this.messageNotice = messageNotice;
    }

    public String getMessageNotice() {
        return messageNotice;
    }

    public long getTime() {
        return time;
    }


    public String getIdentifier() {
        return identifier;
    }

    public String getUserId() {
        return userId;
    }

    public String getData() {
        return data;
    }

    public String getNikeName() {
        return nikeName;
    }


    public String getHeadUrl() {
        return headUrl;
    }

    public SelfCenterMessageData setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
        return this;
    }
    public SelfCenterMessageData setTime(long time) {
        this.time = time;
        return this;
    }

    public SelfCenterMessageData setNikeName(String nikeName) {
        this.nikeName = nikeName;
        return this;
    }

    public SelfCenterMessageData setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public SelfCenterMessageData setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}