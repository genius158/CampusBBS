package com.yan.campusbbs.module.selfcenter.ui.login;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.selfcenter.action.LogInAction;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.module.setting.SettingControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yan on 2017/3/27.
 */

public class LogInFragment extends BaseFragment implements LoginContract.View, SettingControl {
    @Inject
    SettingHelper settingHelper;
    @Inject
    ToastUtils toastUtils;
    @Inject
    SPUtils spUtils;
    @Inject
    LoginPresenter loginPresenter;
    @Inject
    RxBus rxBus;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btb_login)
    Button btbLogin;
    @BindView(R.id.tiel_user_name)
    TextInputEditText tielUserName;
    @BindView(R.id.tiel_user_password)
    TextInputEditText tielUserPassword;
    @BindView(R.id.user_name_layout)
    TextInputLayout userNameLayout;
    @BindView(R.id.user_password_layout)
    TextInputLayout userPasswordLayout;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    public static LogInFragment newInstance() {
        LogInFragment logInFragment = new LogInFragment();
        Bundle args = new Bundle();
        logInFragment.setArguments(args);
        return logInFragment;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_center_login, container, false);
        ButterKnife.bind(this, view);
        daggerInject();
        init();
        initRxAction();
        dataInit();
        return view;
    }

    private void init() {
        skinInit();
    }

    private void dataInit() {

    }

    private void initRxAction() {

    }

    private void skinInit() {
        changeSkin(new ActionChangeSkin(
                spUtils.getInt(Context.MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 0)
        ));
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
    }

    private void daggerInject() {
        DaggerLoginComponent.builder().applicationComponent(((ApplicationCampusBBS) getContext().getApplicationContext())
                .getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
            );
        }
        ColorStateList colorStateList = ColorStateList.valueOf(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
        );
        ViewCompat.setBackgroundTintList(btbLogin, colorStateList);
        ViewCompat.setBackgroundTintList(tielUserName, colorStateList);
        ViewCompat.setBackgroundTintList(tielUserPassword, colorStateList);
        tvRegister.setTextColor(ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId()));
    }


    @OnClick({R.id.btb_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btb_login:
                if (!TextUtils.isEmpty(tielUserName.getText())
                        && !TextUtils.isEmpty(tielUserPassword.getText())) {
                    loginPresenter.login(tielUserName.getText().toString()
                            , tielUserPassword.getText().toString());
                } else {
                    toastUtils.showShort("输入不能为空");
                }
                break;
            case R.id.tv_register:
                break;
        }
    }

    @Override
    public void loginSuccess(LoginInfoData loginInfoData) {
        spUtils.putString(Context.MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                , SharedPreferenceConfig.SESSION_ID, loginInfoData.toString());
        rxBus.post(new LogInAction(true));
    }

    @Override
    public void loginFail() {
        toastUtils.showShort("网络错误");
    }
}
