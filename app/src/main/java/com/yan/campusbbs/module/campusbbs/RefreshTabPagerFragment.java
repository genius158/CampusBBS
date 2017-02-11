package com.yan.campusbbs.module.campusbbs;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class RefreshTabPagerFragment extends BaseRefreshFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String BUNDLE_TAB_SELECT_POSITION = "tabSelectPosition";

    protected final List<PagerTabAdapter.PagerTabItem> pagerTabItem;
    protected FollowViewsAdd followViewsAdd;
    private PagerTabAdapter pagerTabAdapter;
    private RecyclerView pagerBarRecycler;
    private LinearLayoutManager linearLayoutManager;
    private BaseQuickAdapter.OnRecyclerViewItemClickListener pagerTabItemOnClick;

    protected int tabSelectPosition = 0;

    protected RefreshTabPagerFragment() {
        pagerTabItem = new ArrayList<>();
    }

    public void attach(RecyclerView pagerBarRecycler, PagerTabAdapter pagerTabAdapter, View appBar) {
        this.pagerBarRecycler = pagerBarRecycler;
        this.pagerTabAdapter = pagerTabAdapter;

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.pagerBarRecycler.setLayoutManager(linearLayoutManager);
        this.pagerBarRecycler.setAdapter(this.pagerTabAdapter);
        this.pagerTabAdapter.setOnRecyclerViewItemClickListener(getItemClickListener());

        if (followViewsAdd != null)
            followViewsAdd.addFollowView(appBar);
    }

    public void setFollowAdd(FollowViewsAdd followView) {
        this.followViewsAdd = followView;
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener getItemClickListener() {
        return (view, position) -> {
            tabSelectPosition = position;
            if (pagerTabItemOnClick != null) {
                pagerTabItemOnClick.onItemClick(view, position);
            }
            for (int i = 0; i < pagerTabItem.size(); i++) {
                if (i == position) {
                    pagerTabItem.get(i).isSelect = true;
                } else {
                    pagerTabItem.get(i).isSelect = false;
                }
            }
            pagerTabAdapter.notifyDataSetChanged();
        };
    }

    public void setPagerTabItemOnClick(BaseQuickAdapter.OnRecyclerViewItemClickListener pagerTabItemOnClick) {
        this.pagerTabItemOnClick = pagerTabItemOnClick;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        pagerTabAdapter.changeSkin(actionChangeSkin);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        bundle.putInt(BUNDLE_TAB_SELECT_POSITION, tabSelectPosition);
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);

        tabSelectPosition = bundle.getInt(BUNDLE_TAB_SELECT_POSITION);
        pagerBarRecycler.scrollToPosition(tabSelectPosition);
        pagerTabItem.get(tabSelectPosition).isSelect = true;
        pagerTabAdapter.notifyDataSetChanged();
    }
}
