package com.yan.campusbbs.module.campusbbs.data;

import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterFriendData {
    public TIMUserProfile timUserProfile;
    public boolean isSelf;
    public String timMessage;

    public SelfCenterFriendData(TIMUserProfile timUserProfile) {
        this.timUserProfile = timUserProfile;
    }

    public SelfCenterFriendData(TIMUserProfile timUserProfile, String timMessage,boolean isSelf) {
        this.timUserProfile = timUserProfile;
        this.isSelf = isSelf;
        this.timMessage = timMessage;
    }
}
