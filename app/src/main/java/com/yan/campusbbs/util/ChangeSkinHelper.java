package com.yan.campusbbs.util;

import com.yan.campusbbs.rxbusaction.ActionChangeSkin;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yan on 2017/2/9.
 */

public class ChangeSkinHelper {
    private RxBus rxBus;
    private IChangeSkin changeSkin;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ChangeSkinHelper(RxBus rxBus, IChangeSkin changeSkin, CompositeDisposable compositeDisposable) {
        this.changeSkin = changeSkin;
        this.rxBus = rxBus;
        this.compositeDisposable = compositeDisposable;
        initRxSkinAction();
    }


    protected void initRxSkinAction() {
        compositeDisposable.add(rxBus.getEvent(ActionChangeSkin.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionChangeSkin -> {
                    changeSkin.changeSkin(actionChangeSkin);
                }, Throwable::printStackTrace));
    }
}
