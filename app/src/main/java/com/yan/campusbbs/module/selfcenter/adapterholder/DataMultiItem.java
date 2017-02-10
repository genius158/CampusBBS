package com.yan.campusbbs.module.selfcenter.adapterholder;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by yan on 2017/2/10.
 */

public class DataMultiItem extends MultiItemEntity {
    public Object data;
    public DataMultiItem(int type, Object dataObj) {
        setItemType(type);
        data = dataObj;
    }
}
