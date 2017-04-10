package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterMessageData;
import com.yan.campusbbs.module.setting.SkinControl;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.TimeUtils;

import java.util.List;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterMessageAdapter extends BaseQuickAdapter<SelfCenterMessageData, BaseViewHolder> implements SkinControl {
    private Context context;
    private int titleColor = -1;

    public SelfCenterMessageAdapter(Context context, List<SelfCenterMessageData> data) {
        super(R.layout.activity_bbs_self_center_message_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, SelfCenterMessageData item) {
        holder.setText(R.id.tv_message, item.getData());
        String messageName = "";
        if (!TextUtils.isEmpty(item.getNikeName())) {
            messageName = item.getUserId();
        } else if (!TextUtils.isEmpty(item.getIdentifier())) {
            messageName = item.getIdentifier();
        } else if (!TextUtils.isEmpty(item.getUserId())) {
            messageName = item.getUserId();
        }
        holder.setText(R.id.tv_message_name, messageName);
        holder.setText(R.id.tv_time, TimeUtils.getTime(item.getTime()));

        if (titleColor != -1) {
            holder.setTextColor(R.id.tv_message_name
                    , ContextCompat.getColor(context, titleColor)
            );
        }
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        titleColor = actionChangeSkin.getColorPrimaryId();
        notifyDataSetChanged();
    }
}
