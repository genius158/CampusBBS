package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.module.campusbbs.ui.common.topicdetail.TopicDetailActivity;
import com.yan.campusbbs.module.selfcenter.data.PublishData;
import com.yan.campusbbs.repository.DataAddress;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/5.
 */

public class SelfCenterPublishAdapter extends BaseQuickAdapter<TopicData.DataBean.TopicInfoListBean.TopicListBean, BaseViewHolder> {
    Context context;

    @Inject
    public SelfCenterPublishAdapter(Context context, List<TopicData.DataBean.TopicInfoListBean.TopicListBean> data) {
        super(R.layout.activity_bbs_self_center_hot_publish_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, TopicData.DataBean.TopicInfoListBean.TopicListBean item) {
        holder.setText(R.id.tv_publish_title, item.getTopicTitle());
        holder.getView(R.id.container).setOnClickListener(v -> {
            context.startActivity(new Intent(context, TopicDetailActivity.class)
                    .putExtra("title", item.getTopicTitle())
                    .putExtra("topicId", item.getTopicId())
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            );
        });
        if (TextUtils.isEmpty(item.getFileImage())) {
            ((SimpleDraweeView) holder.getView(R.id.iv_publish_img)).setImageURI(item.getUserHeadImg());
        } else {
            ((SimpleDraweeView) holder.getView(R.id.iv_publish_img)).setImageURI(
                    DataAddress.URL_GET_FILE + item.getFileImage());
        }
    }
}
