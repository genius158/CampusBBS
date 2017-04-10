package com.yan.campusbbs.repository.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yan.campusbbs.util.sort.Sort;

import java.io.Serializable;

/**
 * Created by yan on 2017/2/10.
 */

public class DataMultiItem<T> implements MultiItemEntity ,Sort ,Serializable{
    public T data;
    public int type;

    public DataMultiItem(int type, T dataObj) {
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
