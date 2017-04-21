package com.yan.campusbbs.module.campusbbs.ui.job;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.config.CampusLabels;
import com.yan.campusbbs.module.campusbbs.adapter.CampusDataAdapter;
import com.yan.campusbbs.module.campusbbs.adapter.CampusPagerTabAdapter;
import com.yan.campusbbs.module.campusbbs.ui.common.CampusTabPagerFragment;
import com.yan.campusbbs.module.campusbbs.ui.common.CampusTabPagerModule;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionPagerTabClose;
import com.yan.campusbbs.util.AnimationUtils;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

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
    CampusPagerTabAdapter campusPagerTabMoreAdapter;
    @Inject
    SPUtils spUtils;
    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    ToastUtils toastUtils;
    @Inject
    List<DataMultiItem> dataMultiItems;
    @Inject
    CampusDataAdapter multiItemAdapter;
    @Inject
    AnimationUtils animationUtils;
    @BindView(R.id.pager_bar_more_layout)
    FrameLayout pagerBarMoreLayout;
    @BindView(R.id.pager_bar_more)
    FrameLayout pagerBarMore;
    @BindView(R.id.pager_bar_more_arrow)
    ImageView pagerBarMoreArrow;
    @BindView(R.id.pager_bar_more_recycler)
    RecyclerView pagerBarMoreRecycler;


    private int tagPosition;
    private int pageNo = 1;


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void dataInit() {
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("全部", true));
        for (int i = 0; i < CampusLabels.JOB_LABELS.length; i++) {
            pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem(CampusLabels.JOB_LABELS[i]));
        }
        campusPagerTabAdapter.notifyDataSetChanged();
        mPresenter.getTopicList(String.valueOf(pageNo), 3);
        pagerBarMoreRecycler.setLayoutManager(getLayoutManager());
        pagerBarMoreRecycler.setAdapter(campusPagerTabMoreAdapter);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus_bbs_common, container, false);
        ButterKnife.bind(this, view);
        daggerInject();
        init();
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
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(multiItemAdapter);
        multiItemAdapter.setOnLoadMoreListener(() -> {
            if (tagPosition == 0) {
                mPresenter.getTopicList(String.valueOf(++pageNo), 3);
            } else {
                mPresenter.getTopicList(String.valueOf(++pageNo), 3, CampusLabels.LEAN_LABELS[tagPosition - 1]);
            }
        });
    }

    public static JobFragment newInstance() {
        return new JobFragment();
    }

    public JobFragment() {
    }

    @Override
    protected void onItemClick(int position) {
        super.onItemClick(position);
        tagPosition = position;
        pageNo = 1;
        if (position == 0) {
            mPresenter.getTopicList(String.valueOf(pageNo), 3);
        } else {
            mPresenter.getTopicList(String.valueOf(pageNo), 3, CampusLabels.LEAN_LABELS[position - 1]);
        }
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        if (tagPosition == 0) {
            mPresenter.getTopicList(String.valueOf(pageNo), 3);
        } else {
            mPresenter.getTopicList(String.valueOf(pageNo), 3, CampusLabels.LEAN_LABELS[tagPosition - 1]);
        }
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        campusPagerTabAdapter.changeSkin(actionChangeSkin);
    }

    @OnClick({R.id.pager_bar_more_arrow_layout, R.id.pager_bar_more_layout, R.id.pager_bar_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pager_bar_more_arrow_layout:
                onArrowClick();
                break;
            case R.id.pager_bar_more_layout:
                break;
            case R.id.pager_bar_more:
                rxBus.post(new ActionPagerTabClose());
                break;
        }
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    protected List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems() {
        return pagerTabItems;
    }

    @Override
    protected RecyclerView pagerBarRecycler() {
        return pagerBarRecycler;

    }

    @Override
    protected RecyclerView recyclerView() {
        return recyclerView;
    }

    @Override
    protected CampusDataAdapter campusDataAdapter() {
        return multiItemAdapter;
    }

    @Override
    protected List<DataMultiItem> dataMultiItems() {
        return dataMultiItems;
    }


    @Override
    protected int pageNo() {
        return pageNo;
    }

    @Override
    protected CampusPagerTabAdapter campusPagerTabAdapter() {
        return campusPagerTabAdapter;
    }

    @Override
    protected CampusPagerTabAdapter campusPagerTabMoreAdapter() {
        return campusPagerTabMoreAdapter;
    }

    @Override
    protected RxBus rxBus() {
        return rxBus;
    }

    @Override
    protected View appBar() {
        return appBar;
    }

    @Override
    protected SwipeRefreshLayout swipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    protected AnimationUtils animationHelper() {
        return animationUtils;
    }

    @Override
    protected RecyclerView pagerBarMoreRecycler() {
        return pagerBarMoreRecycler;
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

    @Override
    public void netError() {
        toastUtils.showShort("网络错误 ");
        swipeRefreshLayout.setRefreshing(false);
        multiItemAdapter.setEnableLoadMore(false);
    }
}
