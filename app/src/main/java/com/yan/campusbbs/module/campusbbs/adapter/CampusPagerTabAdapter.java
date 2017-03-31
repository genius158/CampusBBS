package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.ui.common.CampusTabPagerFragment;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.module.setting.SkinControl;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/2/7.
 */

public class CampusPagerTabAdapter extends RecyclerView.Adapter<BaseViewHolder> implements SkinControl {
    private ActionChangeSkin actionChangeSkin;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<PagerTabItem> data;

    @Inject
    public CampusPagerTabAdapter(List<PagerTabItem> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        this.actionChangeSkin = actionChangeSkin;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(
                LayoutInflater.from(context).inflate(R.layout.fragment_campus_bbs_tab_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        baseViewHolder.setText(R.id.pager_tab_item_title, data.get(position).title);
        if (actionChangeSkin != null) {
            baseViewHolder.setBackgroundRes(R.id.pager_tab_item_title, actionChangeSkin.getPagerTabBgId());

            ColorStateList colorStateList = AppCompatResources
                    .getColorStateList(context, actionChangeSkin.getPagerTabTextColorId());
            ((TextView) baseViewHolder.getView(R.id.pager_tab_item_title)).setTextColor(colorStateList);
        }
        baseViewHolder.getView(R.id.pager_tab_container).setSelected(data.get(position).isSelect);

        baseViewHolder.getView(R.id.pager_tab_item_title).setTag(position);
        baseViewHolder.getView(R.id.pager_tab_item_title).setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        int position = (int) view.getTag();
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, view, position);
        }
    };

    @Override
    public int getItemCount() {
        return data.size();
    }



    public static class PagerTabItem {
        public String title;
        public boolean isSelect;

        public PagerTabItem(String title) {
            this.title = title;
        }

        public PagerTabItem(String title, boolean isSelect) {
            this.isSelect = isSelect;
            this.title = title;
        }
    }
}