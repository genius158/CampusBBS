package com.yan.campusbbs.module.campusbbs.data;

import com.yan.campusbbs.module.common.data.UserProfile;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class SelfCenterChatData implements Serializable {
    public String text;
    public long time;
    public UserProfile userProfile;

    public SelfCenterChatData(String text, long time) {
        this.text = text;
        this.time = time;
    }

    public SelfCenterChatData setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }
}
