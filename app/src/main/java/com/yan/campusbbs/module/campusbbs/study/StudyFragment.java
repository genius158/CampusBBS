package com.yan.campusbbs.module.campusbbs.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yan.adapter.CustomAdapter;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.util.FragmentSort;
import com.yan.campusbbs.module.campusbbs.IFollowViewsAdd;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapterHelper;
import com.yan.campusbbs.module.selfcenter.adapterholder.SelfCenterAdapterHelper;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ChangeSkinHelper;
import com.yan.campusbbs.util.ChangeSkinModule;
import com.yan.campusbbs.util.IChangeSkin;
import com.yan.campusbbs.util.RxBus;
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
public class StudyFragment extends BaseFragment implements StudyContract.View, IChangeSkin,FragmentSort {
    List<String> strings;
    CustomAdapter adapter;
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
    ChangeSkinHelper changeSkinHelper;

    private StudyContract.Presenter mPresenter;
    private volatile IFollowViewsAdd iFollowViewsAdd;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campusbbs_study, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        daggerInject();
        skinInit();
    }

    private void daggerInject() {
        DaggerStudyComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .changeSkinModule(new ChangeSkinModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        strings = new ArrayList<>();
        strings.add("学习");
        strings.add("学习");
        strings.add("学习");
        strings.add("学习");
        strings.add("学习");
        strings.add("学习");
        strings.add("学习");
        adapter = SelfCenterAdapterHelper.getAdapter(getContext(), strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.setProgressViewOffset(true,
                (int) (getResources().getDimension(R.dimen.action_bar_height) * 1.5)
                , (int) getResources().getDimension(R.dimen.action_bar_height) * 3);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getContext(), R.color.colorAccent)
        );
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.crFEFEFE)
        );


        //----------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        pagerBarRecycler.setLayoutManager(linearLayoutManager);
        CustomAdapter adapter2 = PagerTabAdapterHelper.getAdapter(getContext(), strings, rxBus);
        pagerBarRecycler.setAdapter(adapter2);

        Log.e("iFollowViewsAdd2", " followView: " + iFollowViewsAdd + " appBar:  " + appBar);
        iFollowViewsAdd.addFollowView(appBar);
    }

    protected void skinInit() {
        changeSkin(new ActionChangeSkin(
                SPUtils.getInt(getContext(), MODE_PRIVATE, SPUtils.SHARED_PREFERENCE, SPUtils.SKIN_INDEX, 0)
        ));
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


    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            strings.clear();
            strings.add("学习学习学习学习");
            strings.add("学习学习学习学习");
            strings.add("学习学习学习学习");
            strings.add("学习学习学习学习");
            strings.add("学习学习学习学习");
            strings.add("学习学习学习学习");
            strings.add("学习学习学习学习");
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    public void setFollowAdd(IFollowViewsAdd followView) {
        this.iFollowViewsAdd = followView;
        Log.e("iFollowViewsAdd1", " followView: " + followView + "  " + this.iFollowViewsAdd);

    }


    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorAccentId())
        );
    }

    @Override
    public int getIndex() {
        return 0;
    }
}
