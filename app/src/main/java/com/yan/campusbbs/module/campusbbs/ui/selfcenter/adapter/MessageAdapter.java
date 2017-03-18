package com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.MessageData;
import com.yan.campusbbs.module.setting.SkinControl;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;

import java.util.List;

/**
 * Created by yan on 2017/3/13.
 */

public class MessageAdapter extends BaseQuickAdapter<MessageData, BaseViewHolder> implements SkinControl {
    private Context context;
    private int titleColor = -1;

    public MessageAdapter(Context context, List<MessageData> data) {
        super(R.layout.activity_bbs_self_center_message_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, MessageData item) {
        holder.setText(R.id.tv_message, item.data);
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
