package com.yan.campusbbs.module.filemanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.adapter.CustomAdapter;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.selfcenter.adapterholder.SelfCenterAdapterHelper;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ChangeSkinHelper;
import com.yan.campusbbs.util.ChangeSkinModule;
import com.yan.campusbbs.util.FragmentSort;
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
public class FileManagerFragment extends BaseFragment implements FileManagerContract.View, IChangeSkin, FragmentSort {
    List<String> strings;
    CustomAdapter adapter;
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
        adapter = SelfCenterAdapterHelper.getAdapter(getContext(), strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.setProgressViewOffset(true,
                (int) (getResources().getDimension(R.dimen.action_bar_height) * 1.5)
                , (int) getResources().getDimension(R.dimen.action_bar_height) * 3);

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.crFEFEFE)
        );
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

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            strings.clear();
            strings.add("文件管理文件管理文件管理文件管理");
            strings.add("文件管理文件管理文件管理文件管理");
            strings.add("文件管理文件管理文件管理文件管理");
            strings.add("文件管理文件管理文件管理文件管理");
            strings.add("文件管理文件管理文件管理文件管理");
            strings.add("文件管理文件管理文件管理文件管理");
            strings.add("文件管理文件管理文件管理文件管理");
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    protected void skinInit() {
        changeSkin(new ActionChangeSkin(
                SPUtils.getInt(getContext(), MODE_PRIVATE
                        , SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 0)
        ));
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        appBarBackground.setCardBackgroundColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
        );
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorAccentId())
        );
    }

    @Override
    public int getIndex() {
        return 2;
    }
}
