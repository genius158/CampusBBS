package com.yan.campusbbs.module.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.adapter.CampusDataAdapter;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.module.campusbbs.ui.common.topicdetail.TopicDetailActivity;
import com.yan.campusbbs.module.search.action.ActionChangeSearchText;
import com.yan.campusbbs.module.search.data.SearchData;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.utils.EmptyUtil;
import com.yan.campusbbs.utils.RxBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/3/13.
 */

public class SearchAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {
    private Context context;
    private RxBus rxBus;
    public static final int ITEM_TYPE_SEARCH = 11;

    @Inject
    public SearchAdapter(List<DataMultiItem> data, Context context, RxBus rxBus) {
        super(data);
        addItemType(ITEM_TYPE_SEARCH, R.layout.activity_bbs_search_item);
        addItemType(CampusDataAdapter.ITEM_TYPE_POST_TAG, R.layout.fragment_campus_bbs_item_post_tag);
        this.context = context;
        this.rxBus = rxBus;
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem multiItem) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_SEARCH:
                holder.setText(R.id.tv_search_text, ((SearchData) multiItem).data);
                View container = holder.getView(R.id.tv_search_text);
                container.setTag(multiItem);
                container.setOnClickListener(onClickListener);
                break;
            case CampusDataAdapter.ITEM_TYPE_POST_TAG:
                SimpleDraweeView head = holder.getView(R.id.head);
                if (multiItem.data instanceof TopicData.DataBean.TopicInfoListBean.TopicListBean) {
                    TopicData.DataBean.TopicInfoListBean.TopicListBean topic = (TopicData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;
                    holder.setText(R.id.tv_user_name, (topic.getUserNickname() == null ? topic.getUserAccount()
                            : topic.getUserNickname()));
                    holder.setText(R.id.tv_title, topic.getTopicTitle());
                    holder.setText(R.id.tv_like_num, String.valueOf("点赞(" + EmptyUtil.numObjectEmpty(topic.getLikeCount()) + ")"));
                    holder.setText(R.id.tv_reply_num, String.valueOf("回复(" + EmptyUtil.numObjectEmpty(topic.getCmtCount()) + ")"));
                    holder.setText(R.id.tv_brown_count, String.valueOf("浏览(" + EmptyUtil.numObjectEmpty(topic.getBrowseCount()) + ")"));
                    head.setImageURI(DataAddress.URL_GET_FILE +topic.getUserHeadImg());
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

    private View.OnClickListener onClickListener = v -> {
        SearchData searchData = (SearchData) v.getTag();
        rxBus.post(new ActionChangeSearchText(searchData));
    };

}
