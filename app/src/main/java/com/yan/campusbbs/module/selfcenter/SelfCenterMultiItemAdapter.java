package com.yan.campusbbs.module.selfcenter;

import android.content.Context;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.repository.DataMultiItem;
import com.yan.campusbbs.util.FrescoUtils;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/2/7.
 */
public class SelfCenterMultiItemAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {

    public static final int ITEM_TYPE_SELF_HEADER = 1;
    public static final int ITEM_TYPE_SELF_SELF_DYNAMIC = 2;
    private Context context;

    @Inject
    public SelfCenterMultiItemAdapter(List<DataMultiItem> data, Context context) {
        super(data);
        addItemType(ITEM_TYPE_SELF_HEADER, R.layout.fragment_self_center_bg_header_sign);
        addItemType(ITEM_TYPE_SELF_SELF_DYNAMIC, R.layout.fragment_self_center_self_dynamic_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem multiItem) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_SELF_HEADER:
                SimpleDraweeView imageView = holder.getView(R.id.self_part_one_img);
                imageView.setAspectRatio(1.50f);
                FrescoUtils.display(imageView, String.valueOf(multiItem.data));

                FrescoUtils.display(holder.getView(R.id.self_part_one_header)
                        , String.valueOf(multiItem.data));

                break;
            case ITEM_TYPE_SELF_SELF_DYNAMIC:
                SimpleDraweeView simpleDraweeView = holder.getView(R.id.self_part_one_img);
                FrescoUtils.adjustViewOnImage(context, simpleDraweeView, String.valueOf(multiItem.data));

                break;
        }
    }


}
