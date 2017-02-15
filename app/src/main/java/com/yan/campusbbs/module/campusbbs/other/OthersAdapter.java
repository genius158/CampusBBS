package com.yan.campusbbs.module.campusbbs.other;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.R;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/2/15.
 */

public class OthersAdapter extends BaseQuickAdapter<OthersAdapter.OthersItem, BaseViewHolder> {
    private Context context;

    @Inject
    public OthersAdapter(List<OthersItem> data, Context context) {
        super(R.layout.fragment_campus_bbs_others_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OthersItem othersItem) {
        baseViewHolder.setText(R.id.title, othersItem.title);
        baseViewHolder.setImageDrawable(R.id.img,
                ContextCompat.getDrawable(context, othersItem.imgIds));
    }

    /**
     * Created by yan on 2017/2/15.
     */

    public static class OthersItem {
        public String title;
        public String imgPath;
        public int imgIds;

        public OthersItem(String title, int imgIds) {
            this.title = title;
            this.imgIds = imgIds;
        }
    }
}
