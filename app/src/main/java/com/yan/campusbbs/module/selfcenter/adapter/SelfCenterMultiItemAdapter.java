package com.yan.campusbbs.module.selfcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.SelfCenterActivity;
import com.yan.campusbbs.module.common.pop.PopPhotoView;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.module.selfcenter.data.MainPageData;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.FrescoUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/2/7.
 */
public class SelfCenterMultiItemAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> implements View.OnClickListener {

    public static final int ITEM_TYPE_SELF_HEADER = 1;
    public static final int ITEM_TYPE_SELF_DYNAMIC = 2;
    public static final int ITEM_TYPE_FRIEND_TITLE = 3;
    public static final int ITEM_TYPE_FRIEND_DYNAMIC = 4;
    private Context context;

    private PopPhotoView popPhotoView;

    public void setPopPhotoView(PopPhotoView popPhotoView) {
        this.popPhotoView = popPhotoView;
    }

    @Inject
    public SelfCenterMultiItemAdapter(List<DataMultiItem> data, Context context) {
        super(data);
        addItemType(ITEM_TYPE_SELF_HEADER, R.layout.fragment_self_center_bg_header_sign);
        addItemType(ITEM_TYPE_SELF_DYNAMIC, R.layout.fragment_self_center_self_dynamic_item);
        addItemType(ITEM_TYPE_FRIEND_TITLE, R.layout.fragment_self_center_friend_dynamic_title);
        addItemType(ITEM_TYPE_FRIEND_DYNAMIC, R.layout.fragment_self_center_friend_dynamic_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem multiItem) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_SELF_HEADER:
                SimpleDraweeView imageView = holder.getView(R.id.self_part_one_img);
                imageView.setAspectRatio(1.50f);
                if (multiItem.data instanceof LoginInfoData) {
                    LoginInfoData loginInfoData = (LoginInfoData) multiItem.data;
                    holder.setText(R.id.tv_nike_name, loginInfoData.getData().getUserInfo().getUserNickname());
                    holder.setText(R.id.tv_plus, "等级:" + loginInfoData.getData().getUserInfo().getUserRank());

                    FrescoUtils.display(holder.getView(R.id.self_part_one_header)
                            , String.valueOf(multiItem.data));
                    imageView.setOnClickListener(v -> {
                        if (popPhotoView != null) {
                            popPhotoView.show();
                            popPhotoView.setImageUrl(String.valueOf(multiItem.data));
                        }
                    });
                    holder.setOnClickListener(R.id.self_part_one_header, this);
                }

                break;

            case ITEM_TYPE_FRIEND_TITLE:

                break;
            case ITEM_TYPE_SELF_DYNAMIC:
                MainPageData.DataBean.TopicInfoListBean.TopicListBean selfBean =
                        (MainPageData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;

                SimpleDraweeView selfSimpleDrawee = holder.getView(R.id.self_part_one_img);
                holder.setText(R.id.message_detail, selfBean.getTopicTitle());
                holder.setText(R.id.self_dynamic, selfBean.getTopicContent());
                holder.setText(R.id.tv_brown_count, "浏览(" + selfBean.getCmtCount() + ")");
                FrescoUtils.adjustViewOnImage(context, selfSimpleDrawee, selfBean.getUserHeadImg());
                selfSimpleDrawee.setOnClickListener(v -> {
                    if (popPhotoView != null) {
                        popPhotoView.show();
                        popPhotoView.setImageUrl(selfBean.getUserHeadImg());
                    }
                });
                break;
            case ITEM_TYPE_FRIEND_DYNAMIC:
                MainPageData.DataBean.TopicInfoListBean.TopicListBean otherBean =
                        (MainPageData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;

                SimpleDraweeView simpleDrawee = holder.getView(R.id.self_part_one_img);
                SimpleDraweeView head = holder.getView(R.id.sdv_head);
                head.setImageURI(String.valueOf(multiItem.data));
                holder.setText(R.id.user_name, otherBean.getUserNickname());
                holder.setText(R.id.self_dynamic, otherBean.getTopicTitle());
                holder.setText(R.id.tv_brown_count, "浏览(" + otherBean.getCmtCount() + ")");
                FrescoUtils.adjustViewOnImage(context, simpleDrawee, otherBean.getUserHeadImg());
                simpleDrawee.setOnClickListener(v -> {
                    if (popPhotoView != null) {
                        popPhotoView.show();
                        popPhotoView.setImageUrl(otherBean.getUserHeadImg());
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.self_part_one_header:
                context.startActivity(new Intent(context, SelfCenterActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }
}
