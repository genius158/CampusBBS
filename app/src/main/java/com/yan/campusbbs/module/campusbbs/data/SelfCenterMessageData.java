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
    private long time;

    public SelfCenterMessageData(String data, String identifier) {
        this.data = data;
        this.identifier = identifier;
    }

    public SelfCenterMessageData(String data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public SelfCenterMessageData setTime(long time) {
        this.time = time;
        return this;
    }

    public SelfCenterMessageData(String data, String userId, String nikeName) {
        this.data = data;
        this.nikeName = nikeName;
        this.userId = userId;
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

    public SelfCenterMessageData setNikeName(String nikeName) {
        this.nikeName = nikeName;
        return this;
    }

    public SelfCenterMessageData setData(String data) {
        this.data = data;
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