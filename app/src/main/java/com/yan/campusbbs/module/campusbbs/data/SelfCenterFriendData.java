package com.yan.campusbbs.module.campusbbs.data;

import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterFriendData {
    public TIMUserProfile timUserProfile;
    public boolean isSelf;
    public String message;
    public TIMMessage timMessage;

    public SelfCenterFriendData(TIMUserProfile timUserProfile) {
        this.timUserProfile = timUserProfile;
    }

    public SelfCenterFriendData(TIMUserProfile timUserProfile, String message, boolean isSelf) {
        this.timUserProfile = timUserProfile;
        this.isSelf = isSelf;
        this.message = message;
    }

    public SelfCenterFriendData setTimMessage(TIMMessage timMessage) {
        this.timMessage = timMessage;
        return this;
    }
}
