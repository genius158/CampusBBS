package com.yan.campusbbs.module.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
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
    @BindView(R.id.setting_img_show_control1)
    CheckBox settingImgShowControl1;
    @BindView(R.id.setting_img_show_control2)
    CheckBox settingImgShowControl2;
    @BindView(R.id.setting_img_show_control3)
    CheckBox settingImgShowControl3;
    @BindView(R.id.setting_img_show_control4)
    CheckBox settingImgShowControl4;
    CheckBox[] settingImgShowControl;

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

        settingImgShowControl = new CheckBox[4];
        settingImgShowControl[0] = settingImgShowControl1;
        settingImgShowControl[1] = settingImgShowControl2;
        settingImgShowControl[2] = settingImgShowControl3;
        settingImgShowControl[3] = settingImgShowControl4;

        settingSkinSelect = new RadioButton[4];
        settingSkinSelect[0] = settingSkinSelect1;
        settingSkinSelect[1] = settingSkinSelect2;
        settingSkinSelect[2] = settingSkinSelect3;
        settingSkinSelect[3] = settingSkinSelect4;
        for (RadioButton radioButton : settingSkinSelect) {
            radioButton.setOnCheckedChangeListener(onSkinCheckedChangeListener);
        }
    }

    private CompoundButton.OnCheckedChangeListener onImgShowCheckedChangeListener =
            (compoundButton, isSelect) -> {
                for (CheckBox checkBox : settingImgShowControl) {
                    checkBox.setChecked(isSelect);
                }
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

        for (int i = 0; i < settingImgShowControl.length; i++) {
            if (i == actionChangeSkin.skinIndex) {
                settingImgShowControl[i].setVisibility(View.VISIBLE);
            } else {
                settingImgShowControl[i].setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < settingSkinSelect.length; i++) {
            if (i == actionChangeSkin.skinIndex && !settingSkinSelect[i].isChecked()) {
                settingSkinSelect[i].setChecked(true);
                break;
            }
        }

        for (CheckBox checkBox : settingImgShowControl) {
            checkBox.setOnCheckedChangeListener(onImgShowCheckedChangeListener);
            checkBox.setChecked(spUtils.getBoolean(MODE_PRIVATE
                    , SHARED_PREFERENCE
                    , IMAGE_SHOW_ABLE
                    , true)
            );
        }
    }


    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }
}
