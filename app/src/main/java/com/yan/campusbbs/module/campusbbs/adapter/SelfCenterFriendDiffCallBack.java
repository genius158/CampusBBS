package com.yan.campusbbs.module.campusbbs.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yan.campusbbs.module.campusbbs.data.SelfCenterFriendData;
import com.yan.campusbbs.module.common.utils.DiffCallBack;

import java.util.List;

/**
 * Created by yan on 2017/4/13.
 */

public class SelfCenterFriendDiffCallBack extends DiffCallBack<SelfCenterFriendData> {

    public SelfCenterFriendDiffCallBack(List oldList, List newList) {
        super(oldList, newList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldList.get(oldItemPosition).timUserProfile.getIdentifier().equals(
                newList.get(newItemPosition).timUserProfile.getIdentifier())
        );
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldList.get(oldItemPosition).message.equals(
                newList.get(newItemPosition).message)
        );
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        SelfCenterFriendData oldBean = oldList.get(oldItemPosition);
        SelfCenterFriendData newBean = newList.get(newItemPosition);
        Bundle payload = new Bundle();
        if (!oldBean.message.equals(newBean.message)) {
            payload.putString("message", newBean.message);
        }
        if (oldBean.isSelf != newBean.isSelf) {
            payload.putBoolean("isSelf", newBean.isSelf);
        }

        if (payload.size() == 0) {
            return null;
        }
        return payload;
    }
}
