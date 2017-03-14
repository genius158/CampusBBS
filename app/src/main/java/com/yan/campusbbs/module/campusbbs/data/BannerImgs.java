package com.yan.campusbbs.module.campusbbs.data;

import com.yan.campusbbs.module.campusbbs.adapter.CampusDataAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;

/**
 * Created by Administrator on 2017/3/5.
 */

public class BannerImgs extends DataMultiItem<Object> {
    public BannerImgs(Object dataObj) {
        super(CampusDataAdapter.ITEM_TYPE_BANNER, dataObj);
    }
}
