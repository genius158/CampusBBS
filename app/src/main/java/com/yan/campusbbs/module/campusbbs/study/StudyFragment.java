package com.yan.campusbbs.module.campusbbs.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.adapter.CustomAdapter;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.module.selfcenter.adapterholder.SelfCenterAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class StudyFragment extends BaseFragment implements StudyContract.View {
    List<String> strings;
    CustomAdapter adapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;

    private StudyContract.Presenter mPresenter;


    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_file_manager_study, container, false);
            ButterKnife.bind(this, root);
            init();
        } else {
            if (root.getParent() != null) {
                ((ViewGroup) root.getParent()).removeView(root);
            }
        }
        return root;
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
                (int) getResources().getDimension(R.dimen.action_bar_height)
                , (int) getResources().getDimension(R.dimen.action_bar_height) * 2);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getContext(), R.color.colorAccent)
        );
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.crFEFEFE)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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

}
