package com.yan.campusbbs.module.campusbbs.data;

import android.support.annotation.NonNull;

import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterFriendData implements Comparable<SelfCenterFriendData> {
    public TIMUserProfile timUserProfile;
    public boolean isSelf;
    public String message;
    public TIMMessage timMessage;

    public long timestamp;

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

    public SelfCenterFriendData setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public int compareTo(@NonNull SelfCenterFriendData friendData) {
        if (this.timestamp > friendData.timestamp) {
            return -1;
        } else if (this.timestamp < friendData.timestamp) {
            return 1;
        }
        return 0;
    }
}
