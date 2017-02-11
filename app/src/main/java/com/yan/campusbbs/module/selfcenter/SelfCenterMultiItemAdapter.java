package com.yan.campusbbs.module.selfcenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.setting.AdapterImageControl;
import com.yan.campusbbs.util.FrescoDisplay;
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
                SimpleDraweeView imageView = holder.getView(R.id.self_part_one_img);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.height = (int) (SizeUtils.getFullScreenWidth(context) / 3 * 2);
                imageView.setLayoutParams(layoutParams);
                FrescoDisplay.display(context, imageView
                        , (String) multiItem.data
                        , R.drawable.sample_footer_loading
                        , FrescoDisplay.CENTER_CROP);
                break;
            case ITEM_TYPE_SELF_PUSH_WARD:
                holder.setText(R.id.tv_string, (String) multiItem.data);
                break;
        }
    }
}
