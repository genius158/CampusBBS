package com.yan.campusbbs.module.common.data;

import com.tencent.TIMUserProfile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yan on 2017/4/11.
 */

public class UserProfile implements Serializable {
    public String identifier = "";
    public String nickName = "";
    public String allowType = "";
    public String remark = "";
    public String faceUrl = "";
    public String selfSignature = "";
    public List<String> groupNames = new ArrayList();
    public Map<String, byte[]> customInfo = new HashMap();
    public long gender = 0L;
    public long birthday = 0L;
    public long language = 0L;
    public String location = "";

    public UserProfile(String identifier) {
        this.identifier = identifier;
    }

    public UserProfile(TIMUserProfile userProfile) {
        if (userProfile == null) return;
        this.identifier = userProfile.getIdentifier();
        this.nickName = userProfile.getNickName();
        this.allowType = userProfile.getAllowType().name();
        this.remark = userProfile.getRemark();
        this.faceUrl = userProfile.getFaceUrl();
        this.selfSignature = userProfile.getSelfSignature();
        this.groupNames = userProfile.getFriendGroups();
        this.customInfo = userProfile.getCustomInfo();
        this.gender = userProfile.getGender().getValue();
        this.birthday = userProfile.getBirthday();
        this.location = userProfile.getLocation();
    }
}
