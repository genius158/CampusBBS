package com.yan.campusbbs.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.utils.ACache;
import com.yan.campusbbs.utils.SPUtils;
import com.yan.campusbbs.module.setting.SettingControl;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements SettingControl {
    private static final String TAG = "BaseActivity";
    private String BUNDLE_IS_LOGIN = "isIMLogin";

    protected CompositeDisposable compositeDisposable;

    protected boolean isIMLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        Fresco.initialize(getApplicationContext());

        onReLoadBundle(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private void onReLoadBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(BUNDLE_IS_LOGIN, false)
                    && TextUtils.isEmpty(ImManager.getImManager().getTIM().getLoginUser())) {
                ImManager.getImManager().getSin();
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        skinInit();
    }

    private void skinInit() {
        changeSkin(new ActionChangeSkin(
                sPUtils().getInt(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 0)
        ));
    }

    protected abstract SPUtils sPUtils();

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(getBaseContext()
                            , actionChangeSkin.getColorPrimaryDarkId())
            );
        }
    }

    @Override
    public void finish() {
        super.finish();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public void startActivity(Intent intent) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_IS_LOGIN, isIMLogin);
    }
}
