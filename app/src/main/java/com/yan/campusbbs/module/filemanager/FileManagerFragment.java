package com.yan.campusbbs.module.filemanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.module.filemanager.ui.IjkFullscreenActivity;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class FileManagerFragment extends BaseRefreshFragment implements FileManagerContract.View {
    List<String> strings;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.app_bar_background)
    CardView appBarBackground;

    @Inject
    FileManagerPresenter mPresenter;

    @Inject
    SettingHelper settingHelper;

    @Inject
    SPUtils spUtils;

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
        return view;
    }

    @Override
    protected void onLoadLazy(Bundle reLoadBundle) {
        Log.e("onLoadLazy", "FileManagerLoadLazy:" + reLoadBundle);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);

    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        Log.e("onReload", "FileManagerReload");
    }

    private void daggerInject() {
        DaggerFileManagerComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .fileManagerModule(new FileManagerModule(this))
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter<BaseViewHolder>() {
            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false));
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                holder.setText(android.R.id.text1, strings.get(position));
                holder.setOnClickListener(android.R.id.text1, v ->
                        startActivity(new Intent(getContext(), IjkFullscreenActivity.class))
                );
            }

            @Override
            public int getItemCount() {
                return strings.size();
            }
        });

    }

    public static FileManagerFragment newInstance() {
        return new FileManagerFragment();
    }

    public FileManagerFragment() {
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
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
        appBarBackground.setCardBackgroundColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            appBarBackground.setBackgroundColor(
                    ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
            );
        }
    }

}
