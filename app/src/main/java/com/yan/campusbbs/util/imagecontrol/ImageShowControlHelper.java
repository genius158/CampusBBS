package com.yan.campusbbs.util.imagecontrol;

import com.yan.campusbbs.rxbusaction.ActionImageControl;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yan on 2017/2/9.
 */

public class ImageShowControlHelper {
    private RxBus rxBus;
    private ImageShowControl imageShowControl;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ImageShowControlHelper(RxBus rxBus, ImageShowControl imageShowControl, CompositeDisposable compositeDisposable) {
        this.imageShowControl = imageShowControl;
        this.rxBus = rxBus;
        this.compositeDisposable = compositeDisposable;
        initRxImageControl();
    }

    private void initRxImageControl() {
        compositeDisposable.add(rxBus.getEvent(ActionImageControl.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionImageControl -> {
                    imageShowControl.imageShow(actionImageControl);
                }, Throwable::printStackTrace));
    }
}
