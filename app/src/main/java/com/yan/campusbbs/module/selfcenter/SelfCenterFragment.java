package com.yan.campusbbs.module.selfcenter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


    @Inject
    ChangeSkinHelper changeSkinHelper;
    @Inject
    AppBarHelper appBarHelper;

    private int actionBarPinHeight;
    private boolean isNeedAdjustBar;

    private SelfCenterMultiItemAdapter adapter;

    private final List<DataMultiItem> dataMultiItems;
    private SelfCenterContract.Presenter mPresenter;


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

    public SelfCenterFragment() {
        dataMultiItems = new ArrayList<>();
    }

    private void daggerInject() {
        DaggerSelfCenterComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication()).getApplicationComponent())
                .appBarHelperModule(new AppBarHelperModule(appBar))
                .changeSkinModule(new ChangeSkinModule(this, compositeDisposable))
                .selfCenterModule(new SelfCenterModule())
                .build().inject(this);
    }

    private void init() {
        actionBarPinHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100
                , getResources().getDisplayMetrics());

        attach(swipeRefreshLayout);
    }

    private void dataInit() {
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_HEADER
                        , new String("http://2t.5068.com/uploads/allimg/151104/57-151104141236.jpg")));
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

        adapter = new SelfCenterMultiItemAdapter(dataMultiItems, getContext());
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

    @Override
    public void setPresenter(@NonNull SelfCenterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onRefresh() {
        dataMultiItems.clear();
        dataMultiItems.add(
                new DataMultiItem(SelfCenterMultiItemAdapter.ITEM_TYPE_SELF_HEADER
                        , new String("http://img3.imgtn.bdimg.com/it/u=2681925759,1488026640&fm=23&gp=0.jpg")));
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

            float alphaTrigger = 0.8f;
            float alpha = Math.min(getScrollYDistance() / actionBarPinHeight, alphaTrigger);

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

    public float getScrollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisibleChildView.getHeight();
        return (position) * itemHeight - firstVisibleChildView.getTop();
    }

    @Override
    public int getIndex() {
        return 0;
    }
}
