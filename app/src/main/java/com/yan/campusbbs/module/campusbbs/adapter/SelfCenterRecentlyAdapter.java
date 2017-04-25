package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.selfcenter.data.UserInfoData;
import com.yan.campusbbs.module.selfcenter.ui.friendpage.FriendPageActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/5.
 */

public class SelfCenterRecentlyAdapter extends BaseQuickAdapter<UserInfoData.DataBean.UserDetailInfoBean, BaseViewHolder> {
    Context context;

    @Inject
    public SelfCenterRecentlyAdapter(Context context, List<UserInfoData.DataBean.UserDetailInfoBean> data) {
        super(R.layout.activity_bbs_self_center_recently_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, UserInfoData.DataBean.UserDetailInfoBean item) {
        holder.setText(R.id.tv_user_name, TextUtils.isEmpty(item.getUserNickname())
                ? item.getUserAccount()
                : item.getUserNickname());
        holder.getView(R.id.iv_user_head).setOnClickListener(v -> {
            context.startActivity(new Intent(context, FriendPageActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("userId", item.getUserId())
                    .putExtra("nickName", TextUtils.isEmpty(item.getUserNickname())
                            ?item.getUserAccount()
                            :item.getUserNickname())
            );
        });
        ((SimpleDraweeView) holder.getView(R.id.iv_user_head)).setImageURI(item.getUserHeadImg());
    }
}
