package com.yan.campusbbs.module.campusbbs.study;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.common.CampusPagerTabAdapter;
import com.yan.campusbbs.module.campusbbs.common.CampusTabPagerFragment;
import com.yan.campusbbs.module.campusbbs.common.CampusTabPagerModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionPagerTabClose;
import com.yan.campusbbs.util.AnimationUtils;
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
public class StudyFragment extends CampusTabPagerFragment implements StudyContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pager_bar)
    FrameLayout appBar;
    @BindView(R.id.pager_bar_recycler)
    RecyclerView pagerBarRecycler;

    @BindView(R.id.pager_bar_more_arrow)
    ImageView pagerBarMoreArrow;
    @BindView(R.id.pager_bar_more_layout)
    FrameLayout pagerBarMoreLayout;
    @BindView(R.id.pager_bar_more)
    FrameLayout pagerBarMore;

    @Inject
    AnimationUtils animationUtils;
    @Inject
    List<CampusPagerTabAdapter.PagerTabItem> pagerTabItems;

    @Inject
    RxBus rxBus;
    @Inject
    SPUtils spUtils;
    @Inject
    CampusPagerTabAdapter campusPagerTabAdapter;
    @Inject
    CampusPagerTabAdapter campusPagerTabMoreAdapter;
    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    ImageControl imageControl;
    @Inject
    List<DataMultiItem> dataMultiItems;
    @Inject
    SelfCenterMultiItemAdapter multiItemAdapter;
    @Inject
    StudyPresenter mPresenter;
    @BindView(R.id.pager_bar_more_recycler)
    RecyclerView pagerBarMoreRecycler;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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

    private void dataInit() {
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("全部", true));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("高考冲刺"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("四级"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("计算机"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("航空学院"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("语文"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("数学"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("英语"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("物理"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("化学"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("生物"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("政治"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("地理"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("疯狂英语"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("刘一男"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("快乐学习"));
        campusPagerTabAdapter.notifyDataSetChanged();

        //-----------------------------------------------------------------

        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_DYNAMIC
                        , new String("说说")));

        multiItemAdapter.notifyDataSetChanged();

        pagerBarMoreRecycler.setLayoutManager(getLayoutManager());
        pagerBarMoreRecycler.setAdapter(campusPagerTabMoreAdapter);
    }

    private void daggerInject() {
        DaggerStudyComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .studyFragmentModule(new StudyFragmentModule(this))
                .campusTabPagerModule(new CampusTabPagerModule())
                .build().inject(this);

        setPagerTabItemOnClick(getOnItemClickListener());
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(multiItemAdapter);
    }

    public static StudyFragment newInstance() {
        return new StudyFragment();
    }

    public StudyFragment() {
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
    }

    private OnItemClickListener getOnItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        };
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
    protected RecyclerView recyclerView() {
        return recyclerView;
    }

    @Override
    protected SwipeRefreshLayout swipeRefreshLayout() {
        return swipeRefreshLayout;
    }

}
