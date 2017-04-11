package com.yan.campusbbs.module.selfcenter.ui.register;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yan on 2017/2/15.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    private static final String TAG = "RegisterActivity";

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    RxBus rxBus;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;

    @Inject
    RegisterPresenter registerPresenter;
    @Inject
    ToastUtils toastUtils;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.cv_btn_get_code)
    CardView cvBtnGetCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_code_notice)
    TextView tvCodeNotice;
    @BindView(R.id.et_nike_name)
    EditText etNikeName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_campus)
    EditText etCampus;
    @BindView(R.id.et_birthday)
    EditText etBirthday;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_like)
    EditText etLike;

    private boolean isVerify = false;

    private int time = 60;
    private boolean isAbleToGetCode = true;
    private Disposable verifyTwiceDisposable;

    private boolean canRegisterUserInfoAble = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_main_page_register);
        ButterKnife.bind(this);
        daggerInject();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void daggerInject() {
        DaggerRegisterComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .registerModule(new RegisterModule(this))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        addDisposable(rxBus.getEvent(ImManager.Action.ActionStateGetMessage.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionStateGetMessage -> {
                            switch (actionStateGetMessage.state) {
                                case 1:
                                    isAbleToGetCode = false;
                                    toastUtils.showUIShort("获取验证码成功");
                                    verifyTwiceTrigger();
                                    break;
                                case 0:
                                    isAbleToGetCode = true;
                                    toastUtils.showUIShort("请求超时");
                                    break;
                                case -1:
                                    isAbleToGetCode = true;
                                    toastUtils.showUIShort(actionStateGetMessage.code);
                                    break;
                            }
                        },
                        throwable -> {
                            toastUtils.showUIShort("操作出错");
                            throwable.printStackTrace();
                        })

        );

        addDisposable(rxBus.getEvent(ImManager.Action.ActionCodeVerify.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionCodeVerify -> {
                            switch (actionCodeVerify.state) {
                                case 1:
                                    isVerify = true;
                                    toastUtils.showUIShort("验证成功");
                                    registerUserInfo();
                                    break;
                                case 0:
                                    toastUtils.showUIShort("请求超时");
                                    break;
                                case -1:
                                    toastUtils.showUIShort("验证失败");
                                    break;
                            }
                        },
                        throwable -> {
                            toastUtils.showUIShort("操作出错");
                            throwable.printStackTrace();
                        })

        );

        addDisposable(rxBus.getEvent(ImManager.Action.ActionPWDCommit.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionPWDCommit -> {
                            switch (actionPWDCommit.state) {
                                case 1:
                                    Log.e(TAG, "init: " + "success");
                                    toastUtils.showShort("注册成功");
                                    break;
                                case 0:
                                    toastUtils.showUIShort("请求超时");
                                    break;
                                case -1:
                                    toastUtils.showUIShort("操作失败");
                                    break;
                            }
                        },
                        throwable -> {
                            toastUtils.showUIShort("操作出错");
                            throwable.printStackTrace();
                        })

        );
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText(R.string.register);
        ColorStateList colorStateList = ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        );
        ViewCompat.setBackgroundTintList(etCode, colorStateList);
        ViewCompat.setBackgroundTintList(etPhone, colorStateList);
        ViewCompat.setBackgroundTintList(etBirthday, colorStateList);
        ViewCompat.setBackgroundTintList(etPassword, colorStateList);
        ViewCompat.setBackgroundTintList(cvBtnGetCode, colorStateList);
        ViewCompat.setBackgroundTintList(etEmail, colorStateList);
        ViewCompat.setBackgroundTintList(etLike, colorStateList);
        ViewCompat.setBackgroundTintList(etNikeName, colorStateList);
        ViewCompat.setBackgroundTintList(etCampus, colorStateList);
        ViewCompat.setBackgroundTintList(etSex, colorStateList);
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }

    //验证 验证码
    private void verifyCode() {
        if (!isVerify) {
            toastUtils.showShort("正在注册中...");
            String code = etCode.getText().toString().trim();
            ImManager.getImManager().verifyCode(code);
            return;
        }
        if (canRegisterUserInfoAble) {
            canRegisterUserInfoAble = false;
            registerUserInfo();
        } else {
            toastUtils.showShort("正在注册中...");
        }
    }

    @OnClick({R.id.arrow_back, R.id.cv_btn_get_code, R.id.tv_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.arrow_back:
                finish();
                break;
            case R.id.cv_btn_get_code:
                sendSMSCode();

                break;
            case R.id.tv_btn_register:
                register();
                break;
        }
    }

    private void register() {
        if (!TextUtils.isEmpty(etPassword.getText())
                && !TextUtils.isEmpty(etPhone.getText())
                && !TextUtils.isEmpty(etCode.getText())
                ) {
            verifyCode();
        } else {
            toastUtils.showShort("电话、密码和验证码不能为空");
        }
    }

    private void registerUserInfo() {
        registerPresenter.register(etPhone.getText().toString()
                , etPassword.getText().toString()
                , etNikeName.getText().toString()
                , ""
                , etEmail.getText().toString()
                , etCampus.getText().toString()
                , etBirthday.getText().toString()
        );
    }

    private void sendSMSCode() {
        if (!isVerify) {
            if (isAbleToGetCode) {
                String userPhone = etPhone.getText().toString().trim();
                ImManager.getImManager().getMSMCode(userPhone);
            }
        }
    }

    private void verifyTwiceTrigger() {
        if (verifyTwiceDisposable == null) {
            verifyTwiceDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if (--time < 0) {
                            isAbleToGetCode = true;
                            time = 60;
                            tvCodeNotice.setText("获取验证码");
                            verifyTwiceDisposable.dispose();
                            verifyTwiceDisposable = null;
                        } else {
                            tvCodeNotice.setText(String.valueOf(time));
                        }
                    });
            addDisposable(verifyTwiceDisposable);
        }
    }


    @Override
    public void error(String error) {
        canRegisterUserInfoAble = true;
        addDisposable(toastUtils.showUIShort(error));
    }

    @Override
    public void success() {
        canRegisterUserInfoAble = false;
        Log.e(TAG, "success: " + "注册成功");
        ImManager.getImManager().pwdCommit(etPassword.getText().toString());
        finish();
    }

    @Override
    public void netError() {
        canRegisterUserInfoAble = true;
        toastUtils.showShort("网络错误");
    }
}
