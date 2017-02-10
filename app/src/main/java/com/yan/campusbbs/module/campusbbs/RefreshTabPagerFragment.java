package com.yan.campusbbs.module.campusbbs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.util.ChangeSkin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class RefreshTabPagerFragment extends BaseRefreshFragment implements ChangeSkin, SwipeRefreshLayout.OnRefreshListener {
    protected List<PagerTabAdapter.PagerTabItem> pagerTabItem;
    protected FollowViewsAdd followViewsAdd;
    private PagerTabAdapter pagerTabAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerTabItem = new ArrayList<>();
    }

    public void attach(SwipeRefreshLayout swipeRefreshLayout, RecyclerView pagerBarRecycler, PagerTabAdapter pagerTabAdapter, View appBar) {
        super.attach(swipeRefreshLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        pagerBarRecycler.setLayoutManager(linearLayoutManager);
        pagerBarRecycler.setAdapter(pagerTabAdapter);
        this.pagerTabAdapter = pagerTabAdapter;
        pagerTabAdapter.setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener);
        followViewsAdd.addFollowView(appBar);
    }

    public void setFollowAdd(FollowViewsAdd followView) {
        this.followViewsAdd = followView;
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = (view, position) -> {
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
