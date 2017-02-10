package com.yan.campusbbs.module.selfcenter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.module.AppBarHelper;
import com.yan.campusbbs.module.AppBarHelperModule;
import com.yan.campusbbs.module.selfcenter.adapterholder.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.module.selfcenter.adapterholder.SelfCenterMultiItemAdapterModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ChangeSkinHelper;
import com.yan.campusbbs.util.ChangeSkinModule;
import com.yan.campusbbs.util.FragmentSort;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class SelfCenterFragment extends BaseRefreshFragment implements SelfCenterContract.View, FragmentSort {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.app_bar_background)
    View appBarBackground;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.app_bar)
    FrameLayout appBar;

    private int actionBarPinHeight;
    private float offsetDy;
    private boolean isNeedAdjustBar;

    private List<DataMultiItem> dataMultiItems;

    private SelfCenterContract.Presenter mPresenter;

    @Inject
    AppBarHelper appBarHelper;

    @Inject
    SelfCenterMultiItemAdapter adapter;

    @Inject
    ChangeSkinHelper changeSkinHelper;

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
        init();
        daggerInject();
        dataInit();
        skinInit();
        return view;
    }

    private void daggerInject() {
        DaggerSelfCenterComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication()).getApplicationComponent())
                .appBarHelperModule(new AppBarHelperModule(appBar))
                .changeSkinModule(new ChangeSkinModule(this, compositeDisposable))
                .selfCenterMultiItemAdapterModule(new SelfCenterMultiItemAdapterModule(dataMultiItems))
                .build().inject(this);
    }

    private void init() {
        dataMultiItems = new ArrayList<>();

        actionBarPinHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100
                , getResources().getDisplayMetrics());

        attach(swipeRefreshLayout);
    }

    private void dataInit() {
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_HEADER
                        , new String("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1212/27/c0/16922662_1356570706978.jpg")));
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
        recyclerView.setAdapter(adapter);
        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public static SelfCenterFragment newInstance() {
        SelfCenterFragment selfCenterFragment = new SelfCenterFragment();
        selfCenterFragment.setArguments(new Bundle());
        return new SelfCenterFragment();
    }

    public SelfCenterFragment() {
    }

    @Override
    public void setPresenter(@NonNull SelfCenterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onRefresh() {
        dataMultiItems.clear();
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_HEADER
                        , new String("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1212/27/c0/16922662_1356570706971.jpg")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("发布说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("发布说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("发布说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("发布说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("发布说说")));
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_PUSH_WARD
                        , new String("发布说说")));

        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            appBarHelper.offset(dy);
            offsetDy += (float) dy;
            float alphaTrigger = 0.8f;
            float alpha = Math.min(offsetDy / actionBarPinHeight, alphaTrigger);

            if (alpha <= alphaTrigger) {
                isNeedAdjustBar = true;
                appBarBackground.setAlpha(alpha);
                appBarTitle.setTextColor(
                        Color.argb(255
                                , (int) (255 * (1 - alpha / 8 * 6))
                                , (int) (255 * (1 - alpha / 8 * 6))
                                , (int) (255 * (1 - alpha / 8 * 6))
                        )
                );
            } else if (isNeedAdjustBar) {
                isNeedAdjustBar = false;
                appBarBackground.setAlpha(alphaTrigger);
                appBarTitle.setTextColor(
                        Color.argb(255
                                , (int) (255 * alphaTrigger / 8 * 6)
                                , (int) (255 * alphaTrigger / 8 * 6)
                                , (int) (255 * alphaTrigger / 8 * 6)
                        )
                );
            }
        }
    };

    @Override
    protected void onSaveArguments(Bundle bundle) {
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
    }

    @Override
    public int getIndex() {
        return 0;
    }
}
