package com.yan.campusbbs.module.campusbbs.ui.common.topicdetail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/4/21.
 */

public class TopicDetailActivity extends BaseActivity implements TopicDetailContract.View {
    private static final String TAG = "TopicDetailActivity";

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ToastUtils toastUtils;
    @Inject
    ImageControl imageControl;
    @Inject
    TopicDetailPresenter presenter;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView tvTitle;

    private String title;
    private String topicId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        ButterKnife.bind(this);
        daggerInject();
        imageControl.frescoInit();
        init();
    }

    private void daggerInject() {
        DaggerTopicDetailComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .topicDetailModule(new TopicDetailModule(this))
                .build().inject(this);
    }

    private void init() {
        title = getIntent().getStringExtra("title");
        topicId = getIntent().getStringExtra("topicId");
        presenter.getTopicDetail(topicId);
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        tvTitle.setText(String.valueOf("发布" + title));

        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }


    @Override
    public void netError() {

    }

    @OnClick({R.id.arrow_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.arrow_back:
                finish();
                break;
        }
    }
}