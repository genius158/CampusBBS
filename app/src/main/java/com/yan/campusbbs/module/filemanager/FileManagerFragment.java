package com.yan.campusbbs.module.filemanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseRefreshFragment;
import com.yan.campusbbs.module.common.pop.PopPhotoView;
import com.yan.campusbbs.module.filemanager.data.FileData;
import com.yan.campusbbs.module.filemanager.ui.IjkFullscreenActivity;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.utils.FrescoUtils;
import com.yan.campusbbs.utils.SPUtils;
import com.yan.campusbbs.utils.SizeUtils;
import com.yan.campusbbs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class FileManagerFragment extends BaseRefreshFragment implements FileManagerContract.View {
    List<FileData.DataBean.FileInfoListBean.FileListBean> fileDatas;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.store_house_ptr_frame)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.app_bar_background)
    CardView appBarBackground;
    @BindView(R.id.container)
    View container;

    @Inject
    FileManagerPresenter mPresenter;
    RecyclerView.Adapter adapter;
    @Inject
    SettingHelper settingHelper;

    @Inject
    SPUtils spUtils;
    @Inject
    ToastUtils toastUtils;

    PopPhotoView popPhotoView;

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
        if (mPresenter != null) {
            mPresenter.getImages();
        }
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
        fileDatas = new ArrayList<>();
        popPhotoView = new PopPhotoView(container, toastUtils);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new RecyclerView.Adapter<BaseViewHolder>() {
            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(getContext())
                        .inflate(R.layout.fragment_file_manager_img_item, parent, false));
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                adjustViewOnImage(holder.getView(R.id.sdv_img),
                        DataAddress.URL_GET_FILE + fileDatas.get(position).getFileImage());
                holder.setOnClickListener(R.id.sdv_img, v -> {
                            popPhotoView.setImageUrl(DataAddress.URL_GET_FILE
                                    + fileDatas.get(position).getFileImage());
                            popPhotoView.show();
                        }
                );
            }

            @Override
            public int getItemCount() {
                return fileDatas.size();
            }
        };
        recyclerView.setAdapter(adapter);

    }

    public static FileManagerFragment newInstance() {
        return new FileManagerFragment();
    }

    public FileManagerFragment() {
    }

    @Override
    public void onRefresh() {
        mPresenter.getImages();
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

    @Override
    public void error() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void setVideo(FileData video) {
        if (video.getData() != null
                && video.getData().getFileInfoList() != null
                && video.getData().getFileInfoList().getFileList() != null
                && !video.getData().getFileInfoList().getFileList().isEmpty()) {
            fileDatas.clear();
            fileDatas.addAll(video.getData().getFileInfoList().getFileList());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setImages(FileData video) {
        swipeRefreshLayout.setRefreshing(false);
        if (video.getData() != null
                && video.getData().getFileInfoList() != null
                && video.getData().getFileInfoList().getFileList() != null
                && !video.getData().getFileInfoList().getFileList().isEmpty()) {
            fileDatas.clear();
            fileDatas.addAll(video.getData().getFileInfoList().getFileList());
            adapter.notifyDataSetChanged();
        }
    }

    public void adjustViewOnImage(SimpleDraweeView simpleDraweeView, String url) {
        ViewGroup.LayoutParams simpleDraweeViewLayoutParams = simpleDraweeView.getLayoutParams();
        simpleDraweeViewLayoutParams.height = 0;
        simpleDraweeView.setLayoutParams(simpleDraweeViewLayoutParams);

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                if (qualityInfo.isOfGoodEnoughQuality()) {
                    ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
                    layoutParams.height = (int) ((SizeUtils.getFullScreenWidth(getContext()) - SizeUtils.dp2px(getContext(), 40))
                            * imageInfo.getHeight() / (float) imageInfo.getWidth()/2);
                    simpleDraweeView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setControllerListener(controllerListener)
                .setTapToRetryEnabled(true)
                .build();

        simpleDraweeView.setController(controller);
    }
}
