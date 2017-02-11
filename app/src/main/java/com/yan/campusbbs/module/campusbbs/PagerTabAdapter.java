package com.yan.campusbbs.module.campusbbs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.content.res.AppCompatResources;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.R;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.setting.SkinControl;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/2/7.
 */

public class PagerTabAdapter extends BaseQuickAdapter<PagerTabAdapter.PagerTabItem> implements SkinControl {
    private ActionChangeSkin actionChangeSkin;
    private Context context;

    @Inject
    public PagerTabAdapter(List<PagerTabItem> data, Context context) {
        super(R.layout.study_pager_tab_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PagerTabItem tabItem) {
        baseViewHolder.setText(R.id.tv_string, tabItem.title);
        if (actionChangeSkin != null) {
            baseViewHolder.setBackgroundRes(R.id.tv_string, actionChangeSkin.getPagerTabBgId());
            ColorStateList colorStateList = AppCompatResources
                    .getColorStateList(context, actionChangeSkin.getPagerTabTextColorId());
            ((TextView) baseViewHolder.getView(R.id.tv_string)).setTextColor(colorStateList);
        }
        baseViewHolder.getView(R.id.pager_tab_container).setSelected(tabItem.isSelect);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        this.actionChangeSkin = actionChangeSkin;
        notifyDataSetChanged();
    }

    public static class PagerTabItem {
        public String title;
        public boolean isSelect;

        public PagerTabItem(String title) {
            this.title = title;
        }
    }

}