package com.yan.campusbbs.setting;

import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionImageControl;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yan on 2017/2/9.
 */

public class SettingHelper {
    private RxBus rxBus;
    private SystemSetting systemSetting;
    private CompositeDisposable compositeDisposable;

    @Inject
    public SettingHelper(RxBus rxBus, SystemSetting systemSetting, CompositeDisposable compositeDisposable) {
        this.systemSetting = systemSetting;
        this.rxBus = rxBus;
        this.compositeDisposable = compositeDisposable;
        initRxImageControl();
        initRxSkinAction();
    }

    private void initRxImageControl() {
        compositeDisposable.add(rxBus.getEvent(ActionImageControl.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionImageControl -> {
                    systemSetting.imageShow(actionImageControl);
                }, Throwable::printStackTrace));
    }

    private void initRxSkinAction() {
        compositeDisposable.add(rxBus.getEvent(ActionChangeSkin.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionChangeSkin -> {
                    systemSetting.changeSkin(actionChangeSkin);
                }, Throwable::printStackTrace));
    }
}
