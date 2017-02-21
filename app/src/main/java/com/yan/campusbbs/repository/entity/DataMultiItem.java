package com.yan.campusbbs.repository.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yan.campusbbs.util.sort.Sort;

/**
 * Created by yan on 2017/2/10.
 */

public class DataMultiItem implements MultiItemEntity ,Sort {
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

    @Override
    public int getIndex() {
        return 0;
    }
}
