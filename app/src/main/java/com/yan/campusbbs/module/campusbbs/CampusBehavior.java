package com.yan.campusbbs.module.campusbbs;

import android.content.Context;
import android.util.AttributeSet;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.module.AppBarBehavior;
import com.yan.campusbbs.rxbusaction.ActionCampusBBSFragmentFinish;
import com.yan.campusbbs.rxbusaction.ActionPagerToCampusBBS;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/2/8.
 */

public class CampusBehavior extends AppBarBehavior {
    private Context context;

    @Inject
    RxBus rxBus;

    private CompositeDisposable compositeDisposable;

    public CampusBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        daggerInject();
        initRxBusDisposable();
    }

    private void daggerInject() {
        DaggerCampusBehaviorComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) context.getApplicationContext())
                        .getApplicationComponent())
                .build().inject(this);
    }

    private void initRxBusDisposable() {
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(rxBus
                .getEvent(ActionPagerToCampusBBS.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pagerToCampusBBS -> {
                    show();
                }));
        compositeDisposable.add(rxBus
                .getEvent(ActionCampusBBSFragmentFinish.class)
                .observeOn(Schedulers.io())
                .subscribe(pagerToCampusBBS -> {
                    if (!compositeDisposable.isDisposed()) {
                        compositeDisposable.clear();
                    }
                }));
    }
}