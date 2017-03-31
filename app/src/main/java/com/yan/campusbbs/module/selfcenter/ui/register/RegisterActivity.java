package com.yan.campusbbs.module.selfcenter.ui.register;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.widget.EditText;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yan on 2017/2/15.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

    @Inject
    RegisterPresenter registerPresenter;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.cv_btn_get_code)
    CardView cvBtnGetCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_phone)
    EditText etPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_main_page_register);
        ButterKnife.bind(this);
        daggerInject();
        init();
    }

    private void daggerInject() {
        DaggerRegisterComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).registerModule(new RegisterModule(this))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {

    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(R.string.register);
        ViewCompat.setBackgroundTintList(etCode, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        ));
        ViewCompat.setBackgroundTintList(etPhone, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        ));
        ViewCompat.setBackgroundTintList(etPassword, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        ));
        ViewCompat.setBackgroundTintList(cvBtnGetCode, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        ));
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }


    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }
}
