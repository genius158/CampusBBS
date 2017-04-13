package com.yan.campusbbs.module.common.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by yan on 2017/4/13.
 */

public abstract class DiffCallBack<T> extends DiffUtil.Callback {
    protected List<T> oldList;
    protected List<T> newList;

    public DiffCallBack(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public abstract boolean areItemsTheSame(int oldItemPosition, int newItemPosition);

    @Override
    public abstract boolean areContentsTheSame(int oldItemPosition, int newItemPosition);

    @Nullable
    @Override
    public abstract Object getChangePayload(int oldItemPosition, int newItemPosition);
}