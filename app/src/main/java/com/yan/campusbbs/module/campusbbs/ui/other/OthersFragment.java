package com.yan.campusbbs.module.campusbbs.ui.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.module.campusbbs.adapter.OthersAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class OthersFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    List<OthersAdapter.OthersItem> othersItems;

    @Inject
    OthersAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus_bbs_others, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        daggerInject();
        init();
        initData();
    }

    private void initData() {
        othersItems.add(new OthersAdapter.OthersItem("NBA", R.mipmap.ic_launcher));
        othersItems.add(new OthersAdapter.OthersItem("二手市场", R.mipmap.ic_launcher));
        othersItems.add(new OthersAdapter.OthersItem("NBA", R.mipmap.ic_launcher));

        adapter.notifyDataSetChanged();
    }

    private void daggerInject() {
        DaggerOthersComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .othersFragmentModule(new OthersFragmentModule())
                .build().inject(this);
    }

    private void init() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    public static OthersFragment newInstance() {
        return new OthersFragment();
    }

    public OthersFragment() {
    }


}
