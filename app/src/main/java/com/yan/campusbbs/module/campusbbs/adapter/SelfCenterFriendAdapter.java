package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.TIMUserProfile;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterFriendData;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat.ChatActivity;

import java.util.List;

import javax.inject.Inject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Administrator on 2017/3/5.
 */

public class SelfCenterFriendAdapter extends BaseQuickAdapter<SelfCenterFriendData, BaseViewHolder> {
    Context context;

    @Inject
    public SelfCenterFriendAdapter(Context context, List<SelfCenterFriendData> data) {
        super(R.layout.activity_bbs_self_item_friend, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, SelfCenterFriendData item) {
        TIMUserProfile userProfile = item.timUserProfile;
        View view = holder.getView(R.id.container);
        view.setTag(item);
        view.setOnClickListener(onClickListener);
        String userName;
        if (!TextUtils.isEmpty(userProfile.getNickName())) {
            userName = userProfile.getNickName();
        } else {
            userName = userProfile.getIdentifier();
        }
        holder.setText(R.id.tv_user_name, userName);
        holder.setText(R.id.tv_sign, userProfile.getSelfSignature());

        ((SimpleDraweeView) holder.getView(R.id.sdv_head))
                .setImageURI(userProfile.getFaceUrl());
    }

    private View.OnClickListener onClickListener = v -> {
        SelfCenterFriendData friendData = (SelfCenterFriendData) v.getTag();
        TIMUserProfile userProfile = friendData.timUserProfile;
        context.startActivity(new Intent(context, ChatActivity.class).addFlags(FLAG_ACTIVITY_NEW_TASK)
                .putExtra("identifier", userProfile.getIdentifier())
                .putExtra("identifierType", 1)
        );
    };
}
