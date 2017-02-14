package com.yan.campusbbs.module.campusbbs.study;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.campusbbs.common.CampusPagerTabAdapter;
import com.yan.campusbbs.module.campusbbs.common.CampusTabPagerFragment;
import com.yan.campusbbs.module.campusbbs.common.CampusTabPagerModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.AnimationHelper;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

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
    AnimationHelper animationHelper;
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
        init();
        daggerInject();
        dataInit();
        return view;
    }

    private void dataInit() {
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("全部", true));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        pagerTabItems.add(new CampusPagerTabAdapter.PagerTabItem("学习"));
        campusPagerTabAdapter.notifyDataSetChanged();

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

        attach(recyclerView, pagerTabItems, pagerBarRecycler, campusPagerTabAdapter, appBar, rxBus);
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

    @OnClick({R.id.pager_bar_more_arrow_layout, R.id.pager_bar_more_layout, R.id.pager_bar_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pager_bar_more_arrow_layout:
                onArrowClick();
                break;
            case R.id.pager_bar_more_layout:
                break;
            case R.id.pager_bar_more:
                break;
        }
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
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

    @Override
    protected SwipeRefreshLayout swipeRefreshLayout() {
        return swipeRefreshLayout;
    }


    private ChipsLayoutManager getLayoutManager() {
        return ChipsLayoutManager.newBuilder(getContext())
//              //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
//              .setChildGravity(Gravity.TOP)
//              //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(false)
//              //set maximum views count in a particular row
//              .setMaxViewsInRow(2)
//              //set gravity resolver where you can determine gravity for item in position.
//              //This method have priority over previous one
//              .setGravityResolver(new IChildGravityResolver() {
//                  @Override
//                  public int getItemGravity(int position) {
//                      return Gravity.CENTER;
//                  }
//              })
//              //you are able to break row due to your conditions. Row breaker should return true for that views
//              .setRowBreaker(new IRowBreaker() {
//                  @Override
//                  public boolean isItemBreakRow(@IntRange(from = 0) int position) {
//                      return position == 6 || position == 11 || position == 2;
//                  }
//              })
//              //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
//              .setOrientation(ChipsLayoutManager.HORIZONTAL)
//              // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
//              //STRATEGY_FILL_SPACE or STRATEGY_CENTER
//              .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_SPACE)
//              // whether strategy is applied to last row. FALSE by default
//              .withLastRow(true)
                .build();
    }
}
