package com.yan.campusbbs.module.common.data;

import com.yan.campusbbs.module.selfcenter.data.UserInfoData;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Created by yan on 2017/4/25.
 */

public class VisitorsCacheData implements Serializable {
    public LinkedHashSet<UserInfoData.DataBean.UserDetailInfoBean> topicDetailDatas;

    public VisitorsCacheData(LinkedHashSet<UserInfoData.DataBean.UserDetailInfoBean> topicDetailDatas) {
        this.topicDetailDatas = topicDetailDatas;
    }
}
