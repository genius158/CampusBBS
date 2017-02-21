package com.yan.campusbbs.module.selfcenter.entity;

import com.yan.campusbbs.module.selfcenter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;


/**
 * Created by yan on 2017/2/10.
 */

public class FriendDynamic extends DataMultiItem {
    public FriendDynamic(  Object dataObj) {
        super(SelfCenterMultiItemAdapter.ITEM_TYPE_FRIEND_DYNAMIC, dataObj);
    }

    @Override
    public int getIndex() {
        return 3;
    }
}
