package com.yan.campusbbs.module.selfcenter.data;

import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by yan on 2017/2/10.
 */

public class OtherCenterHeader extends DataMultiItem<Object> {
    public OtherCenterHeader(Object dataObj) {
        super(SelfCenterMultiItemAdapter.ITEM_TYPE_OTHER_HEADER, dataObj);
    }

    @Override
    public int getIndex() {
        return 0;
    }
}
