package com.yan.campusbbs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.MainActivity;
import com.yan.campusbbs.rxbusaction.ActionMainActivityShowComplete;
import com.yan.campusbbs.util.RxBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FlashActivity extends BaseActivity {

    @Inject
    RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        daggerInject();
        initRxBusDisposable();
    }

    public void daggerInject() {
        DaggerFlashComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getApplication())
                        .getApplicationComponent())
                .build().inject(this);
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
}
