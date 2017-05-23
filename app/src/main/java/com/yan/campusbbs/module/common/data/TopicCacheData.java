package com.yan.campusbbs.module.common.data;

import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yan on 2017/4/25.
 */

public class TopicCacheData implements Serializable {
    public LinkedHashSet<TopicDetailData.DataBean.TopicDetailInfoBean> topicDetailDatas;

    public TopicCacheData(LinkedHashSet<TopicDetailData.DataBean.TopicDetailInfoBean> topicDetailDatas) {
        this.topicDetailDatas = topicDetailDatas;
    }
}
