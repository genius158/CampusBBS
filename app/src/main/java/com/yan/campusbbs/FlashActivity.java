package com.yan.campusbbs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;

import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.MainActivity;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionMainActivityShowComplete;
import com.yan.campusbbs.util.ChangeSkinHelper;
import com.yan.campusbbs.util.ChangeSkinModule;
import com.yan.campusbbs.util.IChangeSkin;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class FlashActivity extends BaseActivity implements IChangeSkin {

    @Inject
    RxBus rxBus;

    @Inject
    ChangeSkinHelper changeSkinHelper;
    @BindView(R.id.activity_flash)
    CoordinatorLayout activityFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        ButterKnife.bind(this);
        daggerInject();
        initRxBusDisposable();
        skinInit();
    }

    public void daggerInject() {
        DaggerFlashComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getApplication())
                        .getApplicationComponent())
                .changeSkinModule(new ChangeSkinModule(this, compositeDisposable))
                .build().inject(this);
    }

    protected void skinInit() {
        changeSkin(new ActionChangeSkin(
                SPUtils.getInt(getBaseContext(), MODE_PRIVATE, SPUtils.SHARED_PREFERENCE, SPUtils.SKIN_INDEX, 0)
        ));
    }

    private void initRxBusDisposable() {
        addDisposable(Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    startActivity(new Intent(FlashActivity.this, MainActivity.class));
                }));

        addDisposable(rxBus.getEvent(ActionMainActivityShowComplete.class)
                .subscribe(actionMainActivityShowComplete -> {
                    finish();
                }));
    }


    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        changeSkinHelper.statusBarColorChange(this, actionChangeSkin);
        activityFlash.setBackgroundResource(actionChangeSkin.getColorPrimaryId());
    }
}
