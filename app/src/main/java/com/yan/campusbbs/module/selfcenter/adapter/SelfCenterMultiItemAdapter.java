package com.yan.campusbbs.module.selfcenter.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.FrescoUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/2/7.
 */
public class SelfCenterMultiItemAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {

    public static final int ITEM_TYPE_SELF_HEADER = 1;
    public static final int ITEM_TYPE_SELF_DYNAMIC = 2;
    public static final int ITEM_TYPE_FRIEND_TITLE = 3;
    public static final int ITEM_TYPE_FRIEND_DYNAMIC = 4;
    private Context context;

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
                if ( multiItem.data instanceof LoginInfoData ){
                    LoginInfoData loginInfoData = (LoginInfoData) multiItem.data;

                    FrescoUtils.display(holder.getView(R.id.self_part_one_header)
                            , String.valueOf(multiItem.data));
                }
                FrescoUtils.display(imageView, String.valueOf(multiItem.data));

                FrescoUtils.display(holder.getView(R.id.self_part_one_header)
                        , String.valueOf(multiItem.data));

                break;
            case ITEM_TYPE_SELF_DYNAMIC:
                SimpleDraweeView simpleDraweeView = holder.getView(R.id.self_part_one_img);
                FrescoUtils.adjustViewOnImage(context, simpleDraweeView, String.valueOf(multiItem.data));

                break;
            case ITEM_TYPE_FRIEND_TITLE:

                break;
            case ITEM_TYPE_FRIEND_DYNAMIC:
                SimpleDraweeView simpleDrawee = holder.getView(R.id.self_part_one_img);
                SimpleDraweeView head = holder.getView(R.id.sdv_head);
                head.setImageURI(String.valueOf(multiItem.data));
                holder.setText(R.id.user_name, String.valueOf(multiItem.data));
                FrescoUtils.adjustViewOnImage(context, simpleDrawee, String.valueOf(multiItem.data));

                break;
        }
    }


}
