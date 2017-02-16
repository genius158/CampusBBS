package com.yan.campusbbs.module.setting;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yan.campusbbs.config.SharedPreferenceConfig.IMAGE_SHOW_ABLE;
import static com.yan.campusbbs.config.SharedPreferenceConfig.SHARED_PREFERENCE;

/**
 * Created by yan on 2017/2/15.
 */

public class SettingActivity extends BaseActivity {
    @Inject
    SPUtils spUtils;
    @Inject
    RxBus rxBus;
    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    ImageControl imageControl;

    @BindView(R.id.arrow_back)
    ImageView arrowBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.setting_system_title)
    TextView settingSystemTitle;
    @BindView(R.id.setting_img_show_control)
    CheckBox settingImgShowControl;

    @BindView(R.id.setting_skin_select1)
    RadioButton settingSkinSelect1;
    @BindView(R.id.setting_skin_select2)
    RadioButton settingSkinSelect2;
    @BindView(R.id.setting_skin_select3)
    RadioButton settingSkinSelect3;
    @BindView(R.id.setting_skin_select4)
    RadioButton settingSkinSelect4;
    RadioButton[] settingSkinSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        daggerInject();
        init();
        settingInit();
    }

    private void init() {
        title.setText("设置中心");

        settingSkinSelect = new RadioButton[4];
        settingSkinSelect[0] = settingSkinSelect1;
        settingSkinSelect[1] = settingSkinSelect2;
        settingSkinSelect[2] = settingSkinSelect3;
        settingSkinSelect[3] = settingSkinSelect4;

        CompoundButtonCompat.setButtonTintList(settingSkinSelect1, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), R.color.colorPrimary1)));
        CompoundButtonCompat.setButtonTintList(settingSkinSelect2, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), R.color.colorPrimary2)));
        CompoundButtonCompat.setButtonTintList(settingSkinSelect3, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), R.color.colorPrimary3)));
        CompoundButtonCompat.setButtonTintList(settingSkinSelect4, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), R.color.colorPrimary4)));

        for (RadioButton radioButton : settingSkinSelect) {
            radioButton.setOnCheckedChangeListener(onSkinCheckedChangeListener);
        }

        settingImgShowControl.setOnCheckedChangeListener(onImgShowCheckedChangeListener);
    }

    private CompoundButton.OnCheckedChangeListener onImgShowCheckedChangeListener =
            (compoundButton, isSelect) -> {
                imageControl.setImageShowAble(isSelect);
            };

    private CompoundButton.OnCheckedChangeListener onSkinCheckedChangeListener =
            (compoundButton, isSelect) -> {
                if (!isSelect) {
                    return;
                }
                switch (compoundButton.getId()) {
                    case R.id.setting_skin_select1:
                        spUtils.putInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                                , SharedPreferenceConfig.SKIN_INDEX, 0);
                        rxBus.post(new ActionChangeSkin(0));
                        break;
                    case R.id.setting_skin_select2:
                        spUtils.putInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                                , SharedPreferenceConfig.SKIN_INDEX, 1);
                        rxBus.post(new ActionChangeSkin(1));
                        break;
                    case R.id.setting_skin_select3:
                        spUtils.putInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                                , SharedPreferenceConfig.SKIN_INDEX, 2);
                        rxBus.post(new ActionChangeSkin(2));
                        break;
                    case R.id.setting_skin_select4:
                        spUtils.putInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                                , SharedPreferenceConfig.SKIN_INDEX, 3);
                        rxBus.post(new ActionChangeSkin(3));
                        break;
                }
            };

    private void daggerInject() {
        DaggerSettingComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getApplication())
                        .getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .build()
                .inject(this);
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
        settingSystemTitle.setTextColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );

        for (int i = 0; i < settingSkinSelect.length; i++) {
            if (i == actionChangeSkin.skinIndex && !settingSkinSelect[i].isChecked()) {
                settingSkinSelect[i].setChecked(true);
                break;
            }
        }

        CompoundButtonCompat.setButtonTintList(settingImgShowControl, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())));

        settingImgShowControl.setChecked(spUtils.getBoolean(MODE_PRIVATE
                , SHARED_PREFERENCE
                , IMAGE_SHOW_ABLE
                , true));
    }


    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }
}
