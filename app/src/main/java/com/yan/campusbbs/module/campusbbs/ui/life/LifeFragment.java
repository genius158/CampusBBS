package com.yan.campusbbs.module.campusbbs.ui.life;

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
import com.yan.campusbbs.module.campusbbs.data.PostAll;
import com.yan.campusbbs.module.campusbbs.data.PostTag;
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
public class LifeFragment extends CampusTabPagerFragment implements LifeContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pager_bar)
    FrameLayout appBar;
    @BindView(R.id.pager_bar_recycler)
    RecyclerView pagerBarRecycler;
    @BindView(R.id.pager_bar_more_layout)
    FrameLayout pagerBarMoreLayout;
    @BindView(R.id.pager_bar_more)
    FrameLayout pagerBarMore;
    @BindView(R.id.pager_bar_more_arrow)
    ImageView pagerBarMoreArrow;
    @BindView(R.id.pager_bar_more_recycler)
    RecyclerView pagerBarMoreRecycler;

    @Inject
    LifePresenter mPresenter;
    @Inject
    RxBus rxBus;
    @Inject
    CampusPagerTabAdapter campusPagerTabAdapter;
    @Inject
    CampusPagerTabAdapter campusPagerTabMoreAdapter;
    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    AnimationUtils animationUtils;
    @Inject
    ToastUtils toastUtils;
    @Inject
    List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems;
    @Inject
    SPUtils spUtils;
    @Inject
    List<DataMultiItem> dataMultiItems;
    @Inject
    CampusDataAdapter multiItemAdapter;


    private int tagPosition;
    private int pageNo = 1;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void dataInit() {
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("全部", true));

        for (int i = 0; i < CampusLabels.LIFE_LABELS.length; i++) {
            pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem(CampusLabels.LIFE_LABELS[i]));
        }
        campusPagerTabAdapter.notifyDataSetChanged();
        mPresenter.getTopicList(String.valueOf(pageNo), 1);
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
        DaggerLifeComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .lifeFragmentModule(new LifeFragmentModule(this))
                .campusTabPagerModule(new CampusTabPagerModule())
                .build().inject(this);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(multiItemAdapter);
        multiItemAdapter.setOnLoadMoreListener(() -> {
            if (tagPosition == 0) {
                mPresenter.getTopicList(String.valueOf(++pageNo), 1);
            } else {
                mPresenter.getTopicList(String.valueOf(++pageNo), 1, CampusLabels.LEAN_LABELS[tagPosition - 1]);
            }
        });
    }

    public static LifeFragment newInstance() {
        return new LifeFragment();
    }

    public LifeFragment() {
    }

    @Override
    protected void onItemClick(int position) {
        super.onItemClick(position);
        tagPosition = position;
        pageNo = 1;
        if (position == 0) {
            mPresenter.getTopicList(String.valueOf(pageNo), 1);
        } else {
            mPresenter.getTopicList(String.valueOf(pageNo), 1, CampusLabels.LEAN_LABELS[position - 1]);
        }
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        if (tagPosition == 0) {
            mPresenter.getTopicList(String.valueOf(pageNo), 1);
        } else {
            mPresenter.getTopicList(String.valueOf(pageNo), 1, CampusLabels.LEAN_LABELS[tagPosition - 1]);
        }
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
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
    protected View pagerBarMoreArrow() {
        return pagerBarMoreArrow;
    }

    @Override
    protected View pagerBarMoreLayout() {
        return pagerBarMoreLayout;
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
    public void netError() {
        toastUtils.showShort("网络错误 ");
        swipeRefreshLayout.setRefreshing(false);
        multiItemAdapter.setEnableLoadMore(false);
    }
}
