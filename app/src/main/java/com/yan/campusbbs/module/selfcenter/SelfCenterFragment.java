package com.yan.campusbbs.module.selfcenter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

import com.yan.adapter.CustomAdapter;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.StatedFragment;
import com.yan.campusbbs.module.AppBarHelper;
import com.yan.campusbbs.module.AppBarHelperModule;
import com.yan.campusbbs.module.selfcenter.adapterholder.SelfCenterAdapterHelper;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ChangeSkinHelper;
import com.yan.campusbbs.util.ChangeSkinModule;
import com.yan.campusbbs.util.IChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static dagger.internal.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class SelfCenterFragment extends StatedFragment implements SelfCenterContract.View, IChangeSkin {
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

    private List<String> strings;
    private CustomAdapter adapter;

    private SelfCenterContract.Presenter mPresenter;

    @Inject
    AppBarHelper appBarHelper;

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
        return inflater.inflate(R.layout.fragment_self_center, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        daggerInject();
        skinInit();
    }

    protected void skinInit() {
        changeSkin(new ActionChangeSkin(
                SPUtils.getInt(getContext(), MODE_PRIVATE, SPUtils.SHARED_PREFERENCE, SPUtils.SKIN_INDEX, 0)
        ));
    }

    private void daggerInject() {
        DaggerSelfCenterComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getActivity().getApplication()).getApplicationComponent()
        ).appBarHelperModule(new AppBarHelperModule(appBar))
                .changeSkinModule(new ChangeSkinModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        actionBarPinHeight =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                        , 100
                        , getResources().getDisplayMetrics());
        dataInit();
        adapter = SelfCenterAdapterHelper.getAdapter(getContext(), strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(onScrollListener);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.setProgressViewOffset(true,
                (int) (getResources().getDimension(R.dimen.action_bar_height) * 1.5)
                , (int) getResources().getDimension(R.dimen.action_bar_height) * 3);

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.crFEFEFE)
        );
    }

    private void dataInit() {
        strings = new ArrayList<>();
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
        strings.add("个人中心");
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        bundle.putFloat("offsetDy", offsetDy);
    }

    @Override
    protected void reLoadArguments(Bundle bundle) {
        offsetDy = bundle.getFloat("offsetDy");
        Log.d("reLoadArguments", "reLoadArguments: " + offsetDy);

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

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            strings.clear();
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            strings.add("个人中心个人中心个人中心个人中心");
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    };


    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            appBarHelper.offset(dy);
            offsetDy += dy;
            float alphaTrigger = 0.9f;
            float alpha = Math.min(offsetDy / actionBarPinHeight, alphaTrigger);

            if (alpha < alphaTrigger) {
                appBarBackground.setAlpha(alpha);
                appBarTitle.setTextColor(
                        Color.argb(
                                255
                                , (int) (255 * (1 - alpha / 9 * 8))
                                , (int) (255 * (1 - alpha / 9 * 8))
                                , (int) (255 * (1 - alpha / 9 * 8))
                        )
                );
            }
        }
    };

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorAccentId())
        );
    }
}
