package com.yan.campusbbs.module.selfcenter.data;

import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;


/**
 * Created by yan on 2017/2/10.
 */

public class FriendDynamic extends DataMultiItem<Object> {
    public FriendDynamic(PublishData.DataBean.TopicInfoListBean.TopicListBean dataObj) {
        super(SelfCenterMultiItemAdapter.ITEM_TYPE_FRIEND_DYNAMIC, dataObj);
    }

    @Override
    public int getIndex() {
        return 3;
    }
}
