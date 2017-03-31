package com.yan.campusbbs.module.selfcenter.ui.register;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by yan on 2017/2/15.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    public static String APP_KEY = "1c9e60261fea0";
    public static String APP_SECRET = "56eadbb181aa5792b62d8537331bd971";
    private static final String[] AVATARS = new String[400];

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


    SmsManager smsManager;

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
        SMSSDK.initSDK(this, APP_KEY, APP_SECRET);
    }

    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = Message.obtain();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                addDisposable(Observable.just("验证正确")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(str -> toastUtils.showShort(str)));
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    addDisposable(
                            Observable.just("提交验证码成功")
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(str -> toastUtils.showShort(str)));
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    addDisposable(
                            Observable.just("获取验证码成功")
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(str -> toastUtils.showShort(str))
                    );
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    addDisposable(Observable.just("返回支持发送验证码的国家列表")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(str -> toastUtils.showShort(str)
                            ));
                }
            } else {
                //回调失败
                ((Throwable) data).printStackTrace();
            }
        }
    };

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @OnClick({R.id.arrow_back, R.id.cv_btn_get_code, R.id.tv_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.arrow_back:
                finish();
            case R.id.cv_btn_get_code:
                SMSSDK.registerEventHandler(eventHandler);
                String zh = etPhone.getText().toString().trim();
                SMSSDK.getVerificationCode("86", zh);
                break;
            case R.id.tv_btn_register:
                verifyCode();
                break;
        }
    }

    //验证 验证码
    private void verifyCode() {
        String code = etCode.getText().toString().trim();
        String zh = etPhone.getText().toString().trim();
        SMSSDK.submitVerificationCode("86", zh, code);
    }

}
