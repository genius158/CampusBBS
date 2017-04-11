package com.yan.campusbbs.module.campusbbs.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/4/10.
 */

public class SelfCenterMessageCacheData implements Serializable {
    public List<SelfCenterMessageData> centerMessageDatas;


    public SelfCenterMessageCacheData(List<SelfCenterMessageData> centerMessageDatas) {
        this.centerMessageDatas = centerMessageDatas;
    }

    public SelfCenterMessageCacheData() {
    }

    public List<SelfCenterMessageData> getCenterMessageDatas() {
        return centerMessageDatas;
    }

    public void setCenterMessageDatas(List<SelfCenterMessageData> centerMessageDatas) {
        this.centerMessageDatas = centerMessageDatas;
    }
}
