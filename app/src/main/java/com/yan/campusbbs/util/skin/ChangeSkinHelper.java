package com.yan.campusbbs.util.skin;

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yan on 2017/2/9.
 */

public class ChangeSkinHelper {
    private RxBus rxBus;
    private ChangeSkin changeSkin;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ChangeSkinHelper(RxBus rxBus, ChangeSkin changeSkin, CompositeDisposable compositeDisposable) {
        this.changeSkin = changeSkin;
        this.rxBus = rxBus;
        this.compositeDisposable = compositeDisposable;
        initRxSkinAction();
    }

    private void initRxSkinAction() {
        compositeDisposable.add(rxBus.getEvent(ActionChangeSkin.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionChangeSkin -> {
                    changeSkin.changeSkin(actionChangeSkin);
                }, Throwable::printStackTrace));
    }
}
