package com.yan.campusbbs.module.setting;

import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yan on 2017/2/9.
 */

public class SettingHelper {
    private RxBus rxBus;
    private SettingControl settingControl;
    private CompositeDisposable compositeDisposable;

    @Inject
    public SettingHelper(RxBus rxBus, SettingControl settingControl, CompositeDisposable compositeDisposable) {
        this.settingControl = settingControl;
        this.rxBus = rxBus;
        this.compositeDisposable = compositeDisposable;
        initRxSkinAction();
    }

    private void initRxSkinAction() {
        compositeDisposable.add(rxBus.getEvent(ActionChangeSkin.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionChangeSkin -> {
                    settingControl.changeSkin(actionChangeSkin);
                }, Throwable::printStackTrace));
    }
}
