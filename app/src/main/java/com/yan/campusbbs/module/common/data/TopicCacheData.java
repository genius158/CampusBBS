package com.yan.campusbbs.module.common.data;

import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by yan on 2017/4/25.
 */

public class TopicCacheData implements Serializable {
    public HashSet<TopicDetailData.DataBean.TopicDetailInfoBean.UserTopicInfoBean> topicDetailDatas;

    public TopicCacheData(HashSet<TopicDetailData.DataBean.TopicDetailInfoBean.UserTopicInfoBean> topicDetailDatas) {
        this.topicDetailDatas = topicDetailDatas;
    }
}
