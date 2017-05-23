package com.yan.campusbbs.module.selfcenter.data;

import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;


/**
 * Created by yan on 2017/2/10.
 */

public class BrownHistroyTopicData extends DataMultiItem<Object> {
    public BrownHistroyTopicData(TopicDetailData.DataBean.TopicDetailInfoBean dataObj) {
        super(SelfCenterMultiItemAdapter.ITEM_TYPE_DETAIL_TOPIC, dataObj);
    }

    @Override
    public int getIndex() {
        return 3;
    }
}
