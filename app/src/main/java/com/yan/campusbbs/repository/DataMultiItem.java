package com.yan.campusbbs.repository;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by yan on 2017/2/10.
 */

public class DataMultiItem implements MultiItemEntity {
    public Object data;
    public int type;

    public DataMultiItem(int type, Object dataObj) {
        this.type = type;
        this.data = dataObj;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
