package com.yan.campusbbs.module.campusbbs.data;

import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterFriendData {
    public TIMUserProfile timUserProfile;
    public String timMessage;

    public SelfCenterFriendData(TIMUserProfile timUserProfile) {
        this.timUserProfile = timUserProfile;
    }
    public SelfCenterFriendData(TIMUserProfile timUserProfile,String timMessage) {
        this.timUserProfile = timUserProfile;
        this.timMessage = timMessage;
    }
}
