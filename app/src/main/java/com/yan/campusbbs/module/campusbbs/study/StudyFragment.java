package com.yan.campusbbs.module.campusbbs.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapterModule;
import com.yan.campusbbs.module.campusbbs.RefreshTabPagerFragment;
import com.yan.campusbbs.util.FragmentSort;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapter;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ChangeSkinHelper;
import com.yan.campusbbs.util.ChangeSkinModule;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class StudyFragment extends RefreshTabPagerFragment implements StudyContract.View, FragmentSort {

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
    PagerTabAdapter pagerTabAdapter;
    @Inject
    ChangeSkinHelper changeSkinHelper;

    private StudyContract.Presenter mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        daggerInject();
        dataInit();
        skinInit();
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campusbbs_study, container, false);
    }

    private void dataInit() {
        pagerTabTitle.add("学习");
        pagerTabTitle.add("学习");
        pagerTabTitle.add("学习");
        pagerTabTitle.add("学习");
        pagerTabTitle.add("学习");
        pagerTabTitle.add("学习");
        pagerTabTitle.add("学习");
        pagerTabAdapter.notifyDataSetChanged();
    }

    private void daggerInject() {
        DaggerStudyComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .changeSkinModule(new ChangeSkinModule(this, compositeDisposable))
                .studyFragmentModule(new StudyFragmentModule())
                .pagerTabAdapterModule(new PagerTabAdapterModule(pagerTabTitle))
                .build().inject(this);

        attach(swipeRefreshLayout, pagerBarRecycler, pagerTabAdapter, appBar);
    }

    private void init() {
    }


    public static StudyFragment newInstance() {
        return new StudyFragment();
    }

    public StudyFragment() {
    }

    @Override
    public void setPresenter(@NonNull StudyContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
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
