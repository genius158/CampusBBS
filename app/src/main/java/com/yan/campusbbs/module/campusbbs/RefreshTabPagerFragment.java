package com.yan.campusbbs.module.campusbbs;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class RefreshTabPagerFragment extends BaseRefreshFragment {
    private static final String BUNDLE_TAB_SELECT_POSITION = "tabSelectPosition";
    private static final String BUNDLE_APP_BAR_Y = "appBarY";

    protected final List<PagerTabAdapter.PagerTabItem> pagerTabItem;
    private PagerTabAdapter pagerTabAdapter;
    private RecyclerView pagerBarRecycler;
    private LinearLayoutManager linearLayoutManager;
    private BaseQuickAdapter.OnRecyclerViewItemClickListener pagerTabItemOnClick;
    private View appBar;
    private RxBus rxBus;
    protected int tabSelectPosition = 0;
    private CampusAppHelperAdd campusAppHelperAdd;

    protected RefreshTabPagerFragment() {
        pagerTabItem = new ArrayList<>();
    }

    public void attach(RecyclerView recyclerView, RecyclerView pagerBarRecycler, PagerTabAdapter pagerTabAdapter, View appBar, RxBus rxBus) {
        this.pagerBarRecycler = pagerBarRecycler;
        this.pagerTabAdapter = pagerTabAdapter;
        this.rxBus = rxBus;
        this.appBar = appBar;

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.pagerBarRecycler.setLayoutManager(linearLayoutManager);
        this.pagerBarRecycler.setAdapter(this.pagerTabAdapter);
        this.pagerTabAdapter.setOnRecyclerViewItemClickListener(getItemClickListener());
        campusAppHelperAdd.appHelperAdd(appBar);
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
        bundle.putFloat(BUNDLE_APP_BAR_Y, appBar.getY());
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);

        tabSelectPosition = bundle.getInt(BUNDLE_TAB_SELECT_POSITION, 0);
        pagerBarRecycler.scrollToPosition(tabSelectPosition);
        pagerTabItem.get(tabSelectPosition).isSelect = true;
        pagerTabAdapter.notifyDataSetChanged();

        appBar.setY(bundle.getFloat(BUNDLE_APP_BAR_Y, 0));
    }


    public void setCampusAppHelperAdd(CampusAppHelperAdd campusAppHelperAdd) {
        this.campusAppHelperAdd = campusAppHelperAdd;
    }

}
