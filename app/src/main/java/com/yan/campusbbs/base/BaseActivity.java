package com.yan.campusbbs.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionImageControl;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.setting.SystemSetting;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/2/8.
 */

public class BaseActivity extends AppCompatActivity implements SystemSetting {
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
        imgShowControlInit();
    }

    private final void skinInit() {
        changeSkin(new ActionChangeSkin(
                SPUtils.getInt(this
                        , MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 0)
        ));
    }

    private final void imgShowControlInit() {
        imageShow(new ActionImageControl(
                SPUtils.getBoolean(this
                        , MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.CAN_IMAGE_SHOW, true)
        ));
    }

    @Override
    public void imageShow(ActionImageControl actionImageControl) {

    }

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
