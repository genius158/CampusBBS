package com.yan.campusbbs.module.selfcenter.data;

import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/2/10.
 */

public class FriendTitle extends DataMultiItem<Object> {
    public FriendTitle( ) {
        super(SelfCenterMultiItemAdapter.ITEM_TYPE_FRIEND_TITLE, null);
    }

    @Override
    public int getIndex() {
        return 2;
    }
}
