package com.yan.campusbbs.module.filemanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseRefreshFragment;
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
public class FileManagerFragment extends BaseRefreshFragment implements FileManagerContract.View, FragmentSort {
    List<String> strings;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.app_bar_background)
    CardView appBarBackground;

    private FileManagerContract.Presenter mPresenter;

    @Inject
    ChangeSkinHelper changeSkinHelper;


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_manager, container, false);
        ButterKnife.bind(this, view);
        init();
        daggerInject();
        skinInit();
        return view;
    }

    private void daggerInject() {
        DaggerFileManagerComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .changeSkinModule(new ChangeSkinModule(this, compositeDisposable))
                .build().inject(this);

        attach(swipeRefreshLayout);
    }

    private void init() {
        strings = new ArrayList<>();
        strings.add("文件管理");
        strings.add("文件管理");
        strings.add("文件管理");
        strings.add("文件管理");
        strings.add("文件管理");
        strings.add("文件管理");
        strings.add("文件管理");

    }

    public static FileManagerFragment newInstance() {
        return new FileManagerFragment();
    }

    public FileManagerFragment() {
    }

    @Override
    public void setPresenter(@NonNull FileManagerContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        appBarBackground.setCardBackgroundColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
        );
    }

    @Override
    public int getIndex() {
        return 2;
    }
}
