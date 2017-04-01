package com.yan.campusbbs.module.selfcenter.ui.register;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.setting.SettingActivity;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yan on 2017/2/15.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    public static String APP_KEY = "1c9e60261fea0";
    public static String APP_SECRET = "56eadbb181aa5792b62d8537331bd971";

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
    @BindView(R.id.tv_code_notice)
    TextView tvCodeNotice;
    @BindView(R.id.et_nike_name)
    EditText etNikeName;
    @BindView(R.id.et_sign)
    EditText etSign;
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
        SMSSDK.unregisterAllEventHandler();
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
        addDisposable(Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(aLong -> SMSSDK.registerEventHandler(eventHandler)));
    }

    private EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = Message.obtain();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                addDisposable(Observable.just("回调完成")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(str -> toastUtils.showShort(str)));
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    addDisposable(
                            Observable.just("验证正确")
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(str -> toastUtils.showShort(str)));
                    isVerify = true;
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    isAbleToGetCode = false;
                    verifyTwiceTrigger();
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
                try {
                    JSONObject jsonObject = new JSONObject(((Throwable) data).getMessage());
                    addDisposable(Observable.just(jsonObject.getString("detail"))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(str -> toastUtils.showShort(str)
                            ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        ViewCompat.setBackgroundTintList(etSex, colorStateList);
        ViewCompat.setBackgroundTintList(etSign, colorStateList);
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
            String code = etCode.getText().toString().trim();
            String zh = etPhone.getText().toString().trim();
            SMSSDK.submitVerificationCode("86", zh, code);
        } else {
            toastUtils.showShort("已经验证成功");
        }
    }

    @OnClick({R.id.arrow_back, R.id.cv_btn_get_code, R.id.tv_btn_register, R.id.app_bar_setting_layout})
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
            case R.id.app_bar_setting_layout:
                startActivity(new Intent(getBaseContext(), SettingActivity.class));
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

    private void sendSMSCode() {

        if (!isVerify) {
            if (isAbleToGetCode) {
                String zh = etPhone.getText().toString().trim();
                SMSSDK.getVerificationCode("86", zh);
            }
            return;
        }
        toastUtils.showShort("已经验证成功");
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


}
