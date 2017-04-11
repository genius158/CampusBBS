package com.yan.campusbbs.module.campusbbs.data;

import com.yan.campusbbs.module.common.data.UserProfile;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterMessageData implements Serializable {
    private String data;
    private UserProfile userProfile;
    private String userId;
    private String messageNotice;
    private long time;

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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public SelfCenterMessageData setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public String getData() {
        return data;
    }

    public SelfCenterMessageData setTime(long time) {
        this.time = time;
        return this;
    }

    public SelfCenterMessageData setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}