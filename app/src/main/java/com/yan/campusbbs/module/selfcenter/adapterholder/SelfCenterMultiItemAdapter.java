package com.yan.campusbbs.module.selfcenter.adapterholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.R;
import com.yan.campusbbs.util.SizeUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/2/7.
 */
public class SelfCenterMultiItemAdapter extends BaseMultiItemQuickAdapter<DataMultiItem> {

    public static final int ITEM_TYPE_SELF_HEADER = 1;
    public static final int ITEM_TYPE_SELF_PUSH_WARD = 2;
    private Context context;

    @Inject
    public SelfCenterMultiItemAdapter(List<DataMultiItem> data, Context context) {
        super(data);
        addItemType(ITEM_TYPE_SELF_HEADER, R.layout.fragment_self_center_part_one);
        addItemType(ITEM_TYPE_SELF_PUSH_WARD, R.layout.string_data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem multiItem) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_SELF_HEADER:
                View imageView = holder.getView(R.id.self_part_one_img);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.height = (int) SizeUtils.getFullScreenWidth(context);
                imageView.setLayoutParams(layoutParams);
                break;
            case ITEM_TYPE_SELF_PUSH_WARD:
                holder.setText(R.id.tv_string, (String) multiItem.data);
                break;
        }
    }
}
