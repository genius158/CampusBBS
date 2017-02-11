package com.yan.campusbbs.module.campusbbs;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.module.AppBarHelper;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionPagerToCampusBBS;
import com.yan.campusbbs.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class RefreshTabPagerFragment extends BaseRefreshFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String BUNDLE_TAB_SELECT_POSITION = "tabSelectPosition";
    private static final String BUNDLE_TAB_IS_SHOW = "tabSelectIsShow";

    protected final List<PagerTabAdapter.PagerTabItem> pagerTabItem;
    private PagerTabAdapter pagerTabAdapter;
    private RecyclerView pagerBarRecycler;
    private LinearLayoutManager linearLayoutManager;
    private BaseQuickAdapter.OnRecyclerViewItemClickListener pagerTabItemOnClick;
    private AppBarHelper appBarHelper;
    private RxBus rxBus;
    protected int tabSelectPosition = 0;

    protected RefreshTabPagerFragment() {
        pagerTabItem = new ArrayList<>();
    }

    public void attach(RecyclerView recyclerView, RecyclerView pagerBarRecycler, PagerTabAdapter pagerTabAdapter, View appBar, RxBus rxBus) {
        this.pagerBarRecycler = pagerBarRecycler;
        this.pagerTabAdapter = pagerTabAdapter;
        this.rxBus = rxBus;

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.pagerBarRecycler.setLayoutManager(linearLayoutManager);
        this.pagerBarRecycler.setAdapter(this.pagerTabAdapter);
        this.pagerTabAdapter.setOnRecyclerViewItemClickListener(getItemClickListener());

        recyclerView.addOnScrollListener(onScrollListener);
        appBarHelper = new AppBarHelper(getContext(), appBar);
        initRxBusAction();
    }

    public void initRxBusAction() {
        addDisposable(rxBus.getEvent(ActionPagerToCampusBBS.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pagerToCampusBBS -> {
                            appBarHelper.show();
                        }
                ));
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            appBarHelper.offset(dy);
        }
    };

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
        bundle.putBoolean(BUNDLE_TAB_IS_SHOW, appBarHelper.isShow());
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        tabSelectPosition = bundle.getInt(BUNDLE_TAB_SELECT_POSITION, 0);
        pagerBarRecycler.scrollToPosition(tabSelectPosition);
        pagerTabItem.get(tabSelectPosition).isSelect = true;
        pagerTabAdapter.notifyDataSetChanged();

        if (bundle.getBoolean(BUNDLE_TAB_IS_SHOW, false)) {
            appBarHelper.show();
        }
    }
}
