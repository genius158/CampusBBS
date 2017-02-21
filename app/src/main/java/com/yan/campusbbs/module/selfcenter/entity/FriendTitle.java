package com.yan.campusbbs.module.selfcenter.entity;

import com.yan.campusbbs.module.selfcenter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/2/10.
 */

public class FriendTitle extends DataMultiItem {
    public FriendTitle( ) {
        super(SelfCenterMultiItemAdapter.ITEM_TYPE_FRIEND_TITLE, null);
    }

    @Override
    public int getIndex() {
        return 2;
    }
}
