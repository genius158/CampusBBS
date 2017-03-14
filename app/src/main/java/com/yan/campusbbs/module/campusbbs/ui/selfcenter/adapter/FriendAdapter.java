package com.yan.campusbbs.module.campusbbs.ui.selfcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.data.FriendData;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.chat.ChatActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/5.
 */

public class FriendAdapter extends BaseQuickAdapter<FriendData, BaseViewHolder> {
    Context context;

    @Inject
    public FriendAdapter(Context context, List<FriendData> data) {
        super(R.layout.activity_bbs_self_item_friend, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, FriendData item) {
        View view = holder.getView(R.id.container);
        view.setTag(item);
        view.setOnClickListener(onClickListener);
        ((SimpleDraweeView) holder.getView(R.id.sdv_head))
                .setImageURI("http://uploads.xuexila.com/allimg/1603/703-16031Q55132J7.jpg");
    }

    View.OnClickListener onClickListener = v -> {
        FriendData friendData = (FriendData) v.getTag();
        context.startActivity(new Intent(context, ChatActivity.class));
    };
}
