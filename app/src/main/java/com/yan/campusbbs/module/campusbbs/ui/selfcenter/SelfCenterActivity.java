package com.yan.campusbbs.module.campusbbs.ui.selfcenter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.campusbbs.adapter.SelfCenterPublishAdapter;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.brown.BrownHistoryActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend.FriendsActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.message.MessageActivity;
import com.yan.campusbbs.module.campusbbs.ui.userinfo.UserInfoActivity;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.module.selfcenter.data.PublishData;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yan on 2017/2/15.
 */

public class SelfCenterActivity extends BaseActivity implements SelfCenterContract.View {
    @Inject
    SPUtils spUtils;
    @Inject
    ImageControl imageControl;
    @Inject
    ToastUtils toastUtils;
    @Inject
    SelfCenterPresenter presenter;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_control_layout)
    LinearLayout llControlLayout;
    @BindView(R.id.head)
    SimpleDraweeView head;
    @BindView(R.id.self_hot_post_recycler)
    RecyclerView rvPublishData;

    int pageNo = 1;

    private SelfCenterPublishAdapter publishAdapter;
    private List<PublishData.DataBean.TopicInfoListBean.TopicListBean> selfDynamics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_self_center);
        ButterKnife.bind(this);
        daggerInject();
        imageControl.frescoInit();
        init();

        initData();

    }

    private void initData() {
        if (ACache.get(getBaseContext()).getAsObject(CacheConfig.USER_INFO) != null) {
            LoginInfoData loginInfoData = (LoginInfoData) ACache.get(getBaseContext()).getAsObject(CacheConfig.USER_INFO);
            if (loginInfoData.getData() != null
                    && loginInfoData.getData().getUserInfo() != null) {
                head.setImageURI(loginInfoData.getData().getUserInfo().getUserHeadImg());
                presenter.getSelfPublish(pageNo);
            }
        }
    }

    private void daggerInject() {
        DaggerSelfCenterComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .selfCenterModule(new SelfCenterModule(this))
                .build().inject(this);
    }

    private void init() {
        for (int i = 0; i < llControlLayout.getChildCount(); i++) {
            View view = llControlLayout.getChildAt(i);
            view.setTag(i);
            view.setOnClickListener(onClickListener);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPublishData.setLayoutManager(layoutManager);
        selfDynamics = new ArrayList<>();
        publishAdapter = new SelfCenterPublishAdapter(getBaseContext(), selfDynamics);
        rvPublishData.setAdapter(publishAdapter);
    }

    private View.OnClickListener onClickListener = v -> {
        int position = (int) v.getTag();
        switch (position) {
            case 0:
                startActivity(new Intent(getBaseContext(), FriendsActivity.class));
                break;
            case 1:
                startActivity(new Intent(getBaseContext(), BrownHistoryActivity.class));
                break;
            case 2:
                startActivity(new Intent(getBaseContext(), MessageActivity.class));
                break;
            case 3:
                break;
            case 4:
                break;
        }
    };

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(R.string.self_center);
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }


    @OnClick({R.id.fl_info_container, R.id.arrow_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_info_container:
                startActivity(new Intent(getBaseContext(), UserInfoActivity.class));
                break;
            case R.id.arrow_back:
                finish();
                break;
        }
    }

    @Override
    public void stateSuccess(PublishData publishData) {
        if (publishData == null
                || publishData.getData() == null
                || publishData.getData().getTopicInfoList() == null
                || publishData.getData().getTopicInfoList().getTopicList() == null
                ) {
            return;
        }
        selfDynamics.addAll(publishData.getData().getTopicInfoList().getTopicList());
        Log.e(TAG, "stateSuccess: " + "notifyDataSetChanged");
        publishAdapter.notifyDataSetChanged();
    }

    private static final String TAG = "SelfCenterActivity";

    @Override
    public void stateNetError() {
        toastUtils.showShort("网络错误");
    }

    @Override
    public void stateError() {

    }
}
