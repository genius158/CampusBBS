package com.yan.campusbbs.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.setting.SettingControl;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements SettingControl {
    protected CompositeDisposable compositeDisposable;


    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    protected final void settingInit() {
        skinInit();
    }

    private final void skinInit() {
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
}
