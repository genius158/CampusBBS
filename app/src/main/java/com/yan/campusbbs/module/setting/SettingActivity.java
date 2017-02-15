package com.yan.campusbbs.module.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/2/15.
 */

public class SettingActivity extends BaseActivity {
    @Inject
    SPUtils spUtils;
    @BindView(R.id.arrow_back)
    ImageView arrowBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.common_app_bar)
    CardView commonAppBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        daggerInject();
        settingInit();
    }

    private void daggerInject() {
        DaggerSettingComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).build().inject(this);
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
    }

    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }
}