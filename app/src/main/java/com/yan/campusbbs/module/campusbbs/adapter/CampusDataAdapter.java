package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.module.campusbbs.ui.common.topicdetail.TopicDetailActivity;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.SizeUtils;
import com.yan.campusbbs.widget.banner.Banner;
import com.yan.campusbbs.widget.banner.BannerIndicator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/5.
 */

public class CampusDataAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {
    private Context context;
    public static final int ITEM_TYPE_BANNER = 1;
    public static final int ITEM_TYPE_POST_ALL = 2;
    public static final int ITEM_TYPE_POST_TAG = 3;

    @Inject
    public CampusDataAdapter(List<DataMultiItem> data, Context context) {
        super(data);
        addItemType(ITEM_TYPE_BANNER, R.layout.fragment_campus_bbs_item_banner);
        addItemType(ITEM_TYPE_POST_ALL, R.layout.fragment_campus_bbs_item_post_all);
        addItemType(ITEM_TYPE_POST_TAG, R.layout.fragment_campus_bbs_item_post_tag);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem multiItem) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_BANNER:
                Banner banner = holder.getView(R.id.banner);
                BannerIndicator bannerIndicator = holder.getView(R.id.indicator);
                banner.setInterval(5000);
                banner.setPageChangeDuration(500);
                banner.setBannerDataInit(new Banner.BannerDataInit() {
                    @Override
                    public ImageView initImageView() {
                        return (ImageView) LayoutInflater.from(context).inflate(R.layout.banner_imageview, null);
                    }

                    @Override
                    public void initImgData(ImageView imageView, Object data) {
                        if (data instanceof TopicData.DataBean.TopicInfoListBean.TopicListBean) {
                            TopicData.DataBean.TopicInfoListBean.TopicListBean topic =
                                    (TopicData.DataBean.TopicInfoListBean.TopicListBean) data;

                            ((SimpleDraweeView) imageView).setImageURI(topic.getUserHeadImg());
                            imageView.setOnClickListener(v -> {
                                context.startActivity(new Intent(context, TopicDetailActivity.class)
                                        .putExtra("title", topic.getTopicTitle())
                                        .putExtra("topicId", topic.getTopicId())
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            });
                        }
                    }
                });
                bannerIndicator.setIndicatorSource(
                        ContextCompat.getDrawable(context, R.drawable.banner_indicator_select),
                        ContextCompat.getDrawable(context, R.drawable.banner_indicator_unselect),
                        SizeUtils.dp2px(context, 8)//widthAndHeight
                );
                banner.setOnBannerItemClickListener(position ->
                        Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show());
                banner.setDataSource(multiItem.data);
                banner.attachIndicator(bannerIndicator);
                banner.resumeScroll();
                break;
            case ITEM_TYPE_POST_ALL:
                SimpleDraweeView img = holder.getView(R.id.img);
                if (multiItem.data instanceof TopicData.DataBean.TopicInfoListBean.TopicListBean) {
                    TopicData.DataBean.TopicInfoListBean.TopicListBean topic = (TopicData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;
                    holder.setText(R.id.tv_title, topic.getTopicTitle());
                    holder.setText(R.id.tv_content, topic.getTopicContent());
                    img.setImageURI(topic.getUserHeadImg());
                    holder.getView(R.id.container).setOnClickListener(v -> {
                        context.startActivity(new Intent(context, TopicDetailActivity.class)
                                .putExtra("title", topic.getTopicTitle())
                                .putExtra("topicId", topic.getTopicId())
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                    });
                }
                break;

            case ITEM_TYPE_POST_TAG:
                SimpleDraweeView head = holder.getView(R.id.head);
                if (multiItem.data instanceof TopicData.DataBean.TopicInfoListBean.TopicListBean) {
                    TopicData.DataBean.TopicInfoListBean.TopicListBean topic = (TopicData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;
                    holder.setText(R.id.tv_user_name, (topic.getUserNickname() == null ? topic.getUserAccount()
                            : topic.getUserNickname()));
                    holder.setText(R.id.tv_title, topic.getTopicTitle());
                    head.setImageURI(topic.getUserHeadImg());
                    holder.getView(R.id.container).setOnClickListener(v -> {
                        context.startActivity(new Intent(context, TopicDetailActivity.class)
                                .putExtra("title", topic.getTopicTitle())
                                .putExtra("topicId", topic.getTopicId())
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                    });
                }
                break;
        }
    }
}
