package com.yan.campusbbs.module.campusbbs.job;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.CampusPagerTabAdapter;
import com.yan.campusbbs.module.campusbbs.CampusTabPagerFragment;
import com.yan.campusbbs.module.campusbbs.CampusTabPagerModule;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.AnimationHelper;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class JobFragment extends CampusTabPagerFragment implements JobContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pager_bar)
    FrameLayout appBar;
    @BindView(R.id.pager_bar_recycler)
    RecyclerView pagerBarRecycler;
    @Inject
    JobPresenter mPresenter;
    @Inject
    RxBus rxBus;
    @Inject
    List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems;
    @Inject
    CampusPagerTabAdapter campusPagerTabAdapter;
    @Inject
    SPUtils spUtils;
    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    AnimationHelper animationHelper;
    @BindView(R.id.pager_bar_more_layout)
    FrameLayout pagerBarMoreLayout;
    @BindView(R.id.pager_bar_more)
    FrameLayout pagerBarMore;
    @BindView(R.id.pager_bar_more_arrow)
    ImageView pagerBarMoreArrow;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void dataInit() {
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("全部", true));

        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("工作"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("工作"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("工作"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("工作"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("工作"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("工作"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("工作"));

        campusPagerTabAdapter.notifyDataSetChanged();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus_bbs_common, container, false);
        ButterKnife.bind(this, view);
        init();
        daggerInject();
        dataInit();
        return view;
    }

    private void daggerInject() {
        DaggerJobComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .jobFragmentModule(new JobFragmentModule(this))
                .campusTabPagerModule(new CampusTabPagerModule())
                .build().inject(this);

        attach(recyclerView, pagerTabItems, pagerBarRecycler, campusPagerTabAdapter, appBar, rxBus);
    }

    private void init() {

    }

    public static JobFragment newInstance() {
        return new JobFragment();
    }

    public JobFragment() {
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    protected SwipeRefreshLayout swipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        campusPagerTabAdapter.changeSkin(actionChangeSkin);
    }

    @Override
    protected AnimationHelper animationHelper() {
        return animationHelper;
    }

    @Override
    protected View pagerBarMore() {
        return pagerBarMore;
    }

    @Override
    protected View pagerBarMoreArrow() {
        return pagerBarMoreArrow;
    }

    @Override
    protected View pagerBarMoreLayout() {
        return pagerBarMoreLayout;
    }

    @OnClick({R.id.pager_bar_more_arrow, R.id.pager_bar_more_layout, R.id.pager_bar_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pager_bar_more_arrow:
                onArrowClick();
                break;
            case R.id.pager_bar_more_layout:
                break;
            case R.id.pager_bar_more:
                break;
        }
    }

}
