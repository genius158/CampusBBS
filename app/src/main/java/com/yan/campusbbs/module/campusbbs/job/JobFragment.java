package com.yan.campusbbs.module.campusbbs.job;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapterModule;
import com.yan.campusbbs.module.campusbbs.RefreshTabPagerFragment;
import com.yan.campusbbs.util.setting.SettingHelper;
import com.yan.campusbbs.util.setting.SettingModule;
import com.yan.campusbbs.util.fragmentsort.FragmentSort;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapter;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class JobFragment extends RefreshTabPagerFragment implements JobContract.View, FragmentSort {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pager_bar)
    FrameLayout appBar;
    @BindView(R.id.pager_bar_recycler)
    RecyclerView pagerBarRecycler;

    @Inject
    RxBus rxBus;
    @Inject
    PagerTabAdapter pagerTabAdapter;
    @Inject
    SettingHelper changeSkinHelper;

    private JobContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void dataInit() {
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("工作"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("工作"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("工作"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("工作"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("工作"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("工作"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("工作"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("工作"));

        pagerTabAdapter.notifyDataSetChanged();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campusbbs_job, container, false);
        ButterKnife.bind(this, view);
        init();
        daggerInject();
        dataInit();
        settingInit();
        return view;
    }

    private void daggerInject() {
        DaggerJobComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .settingModule(new SettingModule(this,  compositeDisposable))
                .jobFragmentModule(new JobFragmentModule())
                .pagerTabAdapterModule(new PagerTabAdapterModule(pagerTabItem))
                .build().inject(this);

        attach(swipeRefreshLayout, pagerBarRecycler, pagerTabAdapter, appBar);
    }

    private void init() {

    }

    public static JobFragment newInstance() {
        return new JobFragment();
    }

    public JobFragment() {
    }

    @Override
    public void setPresenter(@NonNull JobContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        pagerTabAdapter.changeSkin(actionChangeSkin);
    }

    @Override
    public int getIndex() {
        return 2;
    }
}
