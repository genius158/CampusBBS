package com.yan.campusbbs.module.selfcenter.ui.mainpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.AppBarHelper;
import com.yan.campusbbs.module.AppBarHelperModule;
import com.yan.campusbbs.module.common.utils.DiffCallBack;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.module.selfcenter.data.FriendTitle;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.module.selfcenter.data.SelfCenterHeader;
import com.yan.campusbbs.module.setting.AdapterImageControl;
import com.yan.campusbbs.module.setting.SettingActivity;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionSelfDataSuccess;
import com.yan.campusbbs.rxbusaction.ActionSelfSearchControl;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;
import com.yan.campusbbs.util.sort.SortUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class SelfCenterFragment extends BaseRefreshFragment implements SelfCenterContract.View {
    private static final String TAG = "SelfCenterFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.app_bar_background)
    CardView appBarBackground;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.app_bar)
    FrameLayout appBar;
    @BindView(R.id.fl_container)
    View container;
    @Inject
    List<DataMultiItem> dataMultiItems;
    @BindView(R.id.app_bar_setting)
    ImageView appBarSetting;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.app_bar_search_layout)
    FrameLayout appBarSearchLayout;
    @BindView(R.id.app_bar_search_main_layout)
    FrameLayout appBarSearchMainLayout;

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    RxBus rxBus;
    @Inject
    AdapterImageControl adapterImageControl;
    @Inject
    AppBarHelper appBarHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ToastUtils toastUtils;
    @Inject
    SelfCenterPresenter mPresenter;
    @Inject
    SelfCenterMultiItemAdapter adapter;

    private int actionBarPinHeight;
    private boolean isNeedAdjustBar;

    int pageNo = 1;

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.start();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_center, container, false);
        ButterKnife.bind(this, view);
        daggerInject();
        init();
        initRxAction();
        dataInit();
        return view;
    }

    private void initRxAction() {
        addDisposable(rxBus.getEvent(ActionSelfSearchControl.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionSelfSearchControl -> {
                    appBarSearchMainLayout.setVisibility((actionSelfSearchControl.isShow) ? View.VISIBLE : View.GONE);
                }));
    }

    public SelfCenterFragment() {
        dataMultiItems = new ArrayList<>();
    }

    private void daggerInject() {
        DaggerSelfCenterComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication()).getApplicationComponent())
                .appBarHelperModule(new AppBarHelperModule(appBar))
                .settingModule(new SettingModule(this, compositeDisposable))
                .selfCenterModule(new SelfCenterModule(this, container, compositeDisposable))
                .build().inject(this);
    }

    @Override
    protected void onLoadLazy(Bundle reLoadBundle) {
        super.onLoadLazy(reLoadBundle);
        Log.e(TAG, "onLoadLazy: SelfCenterLoadLazy " + reLoadBundle);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        bundle.putFloat("getScrollYDistance", getScrollYDistance());
        Log.e(TAG, "onSaveArguments: getScrollYDistance " + getScrollYDistance());
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        Log.e(TAG, "onReloadArguments: getScrollYDistance " + bundle.getFloat("getScrollYDistance"));
    }

    private void init() {
        actionBarPinHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100
                , getResources().getDisplayMetrics());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.clearOnScrollListeners();
        adapterImageControl.attachRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(getOnScrollListener());
        adapter.setEnableLoadMore(false);

    }

    public static SelfCenterFragment newInstance() {
        SelfCenterFragment selfCenterFragment = new SelfCenterFragment();
        selfCenterFragment.setArguments(new Bundle());
        return selfCenterFragment;
    }

    private void dataInit() {
        mPresenter.getMainPageData(pageNo);
        dataMultiItems.add(new FriendTitle());
        SortUtils.sort(dataMultiItems);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        mPresenter.getMainPageData(pageNo);
    }

    private RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                appBarHelper.offset(dy);

                float alphaTrigger = 0.8f;
                float alpha = Math.min(getScrollYDistance() / actionBarPinHeight, alphaTrigger);

                if (alpha <= alphaTrigger) {
                    isNeedAdjustBar = true;
                    appBarBackground.setAlpha(alpha);
                    int color = Color.argb(255
                            , (int) (255 * (1 - alpha / 8 * 6))
                            , (int) (255 * (1 - alpha / 8 * 6))
                            , (int) (255 * (1 - alpha / 8 * 6)));

                    appBarTitle.setTextColor(color);
                    appBarSetting.setBackgroundColor(color);
                    btnSearch.setBackgroundColor(color);

                } else if (isNeedAdjustBar) {
                    isNeedAdjustBar = false;
                    appBarBackground.setAlpha(alphaTrigger);
                    int color = Color.argb(255
                            , (int) (255 * alphaTrigger / 8 * 6)
                            , (int) (255 * alphaTrigger / 8 * 6)
                            , (int) (255 * alphaTrigger / 8 * 6)
                    );
                    appBarTitle.setTextColor(color);
                    appBarSetting.setBackgroundColor(color);
                    btnSearch.setBackgroundColor(color);

                }
            }
        };
    }

    public float getScrollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);

        if (firstVisibleChildView != null) {
            int itemHeight = firstVisibleChildView.getHeight();
            return (position) * itemHeight - firstVisibleChildView.getTop();
        }
        return 0;
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            appBarBackground.setBackgroundColor(
                    ContextCompat.getColor(getContext(), R.color.crFFFFFF)
            );
        }
    }

    @OnClick({R.id.app_bar_setting_layout, R.id.app_bar_search_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_bar_setting_layout:
                startActivity(new Intent(SelfCenterFragment.this.getContext(), SettingActivity.class));
                break;

            case R.id.app_bar_search_layout:
                startActivity(new Intent(SelfCenterFragment.this.getContext(), SettingActivity.class));
                break;
        }
    }

    private boolean isPostSelfData;

    @Override
    public void dataSuccess(List<DataMultiItem> dataMultiItems) {
        if (pageNo == 1) {
            swipeRefreshLayout.setRefreshing(false);
            if (!isPostSelfData) {
                isPostSelfData = true;
                rxBus.post(new ActionSelfDataSuccess());
            }
            this.dataMultiItems.clear();
            this.dataMultiItems.add(new SelfCenterHeader((LoginInfoData) ACache.get(getContext()).getAsObject(CacheConfig.USER_INFO)));
            this.dataMultiItems.addAll(dataMultiItems);

            adapter.notifyDataSetChanged();
            if (dataMultiItems.size() > 3) {
                adapter.setEnableLoadMore(true);
                adapter.setOnLoadMoreListener(
                        () -> {
                            recyclerView.postDelayed(
                                    () -> {
                                        mPresenter.getMainPageData(++pageNo);
                                    }, 100);
                        });
            }
        } else {
            adapter.loadMoreComplete();
            if (dataMultiItems == null || dataMultiItems.isEmpty()) {
                adapter.setEnableLoadMore(false);
            }
            int tempSize = this.dataMultiItems.size();
            this.dataMultiItems.addAll(dataMultiItems);
            adapter.notifyItemRangeInserted(tempSize, this.dataMultiItems.size()-tempSize);
        }
    }

    @Override
    public void dataError() {
        toastUtils.showShort("网络错误");
        if (pageNo == 1) {
            swipeRefreshLayout.setRefreshing(false);
        } else {
            adapter.loadMoreComplete();
        }
    }
}
