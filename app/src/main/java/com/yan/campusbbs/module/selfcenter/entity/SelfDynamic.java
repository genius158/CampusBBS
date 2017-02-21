package com.yan.campusbbs.module.selfcenter.entity;

import com.yan.campusbbs.module.selfcenter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/2/10.
 */

public class SelfDynamic extends DataMultiItem {
    public SelfDynamic(Object dataObj) {
        super(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC, dataObj);
    }

    @Override
    public int getIndex() {
        return 1;
    }
}
