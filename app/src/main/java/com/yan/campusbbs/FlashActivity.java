package com.yan.campusbbs;

import android.content.Intent;
import android.os.Bundle;

import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.MainActivity;
import com.yan.campusbbs.rxbusaction.ActionMainActivityShowComplete;
import com.yan.campusbbs.util.RxBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class FlashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        initRxBusDisposable();
    }

    private void initRxBusDisposable() {
        addDisposable(Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    startActivity(new Intent(FlashActivity.this, MainActivity.class));
                }));

        addDisposable(RxBus.getInstance().getEvent(ActionMainActivityShowComplete.class)
                .subscribe(actionMainActivityShowComplete -> {
                    finish();
                }));
    }
}
