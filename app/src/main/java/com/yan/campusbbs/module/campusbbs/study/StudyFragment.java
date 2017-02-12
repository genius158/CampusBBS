package com.yan.campusbbs.module.campusbbs.study;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapterModule;
import com.yan.campusbbs.module.campusbbs.TabPagerFragment;
import com.yan.campusbbs.module.selfcenter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.setting.ImageControl;
import com.yan.campusbbs.setting.SettingHelper;
import com.yan.campusbbs.setting.SettingModule;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapter;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class StudyFragment extends TabPagerFragment implements StudyContract.View {

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
    SPUtils spUtils;
    @Inject
    PagerTabAdapter pagerTabAdapter;
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

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus_bbs_study, container, false);
        ButterKnife.bind(this, view);
        init();
        daggerInject();
        dataInit();
        return view;
    }

    private void dataInit() {
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("全部",true));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("学习"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("学习"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("学习"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("学习"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("学习"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("学习"));
        pagerTabItem.add(new PagerTabAdapter.PagerTabItem("学习"));
        pagerTabAdapter.notifyDataSetChanged();

        //-----------------------------------------------------------------

        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("说说")));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(multiItemAdapter);
    }

    private void daggerInject() {
        DaggerStudyComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .studyFragmentModule(new StudyFragmentModule(this))
                .pagerTabAdapterModule(new PagerTabAdapterModule(pagerTabItem))
                .build().inject(this);

        attach(recyclerView,pagerBarRecycler, pagerTabAdapter, appBar,rxBus);
        setPagerTabItemOnClick(getOnRecyclerViewItemClickListener());
    }

    private void init() {
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
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
        return (view, position) -> {
            if (position == 0) {
                spUtils.putInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 0);
                rxBus.post(new ActionChangeSkin(0));

            } else if (position == 1) {
                spUtils.putInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 1);
                rxBus.post(new ActionChangeSkin(1));

            } else if (position == 2) {
                spUtils.putInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 2);
                rxBus.post(new ActionChangeSkin(2));

            } else if (position == 3) {
                spUtils.putInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 3);
                rxBus.post(new ActionChangeSkin(3));

            } else if (position == 4) {
                imageControl.setImageShowAble(false);


            } else if (position == 5) {
                imageControl.setImageShowAble(true);
            }
        };
    }
}
