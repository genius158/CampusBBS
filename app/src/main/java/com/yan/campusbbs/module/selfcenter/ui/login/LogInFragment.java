package com.yan.campusbbs.module.selfcenter.ui.login;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.setting.SettingControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yan on 2017/3/27.
 */

public class LogInFragment extends BaseFragment implements LoginContract.View, SettingControl {
    @Inject
    SettingHelper settingHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    LoginPresenter loginPresenter;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

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
        title.setText("登录");
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
    }

}
