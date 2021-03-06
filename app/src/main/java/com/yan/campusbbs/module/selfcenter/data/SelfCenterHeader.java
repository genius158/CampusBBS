package com.yan.campusbbs.module.selfcenter.data;

import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/2/10.
 */

public class SelfCenterHeader extends DataMultiItem<Object> {
    public SelfCenterHeader(Object dataObj) {
        super(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_HEADER, dataObj);
    }

    @Override
    public int getIndex() {
        return 0;
    }
}
