package com.yan.campusbbs.util.imagecontrol;

import com.yan.campusbbs.util.RxBus;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/2/9.
 */

@Module
public class ImageShowControlModule {
    private final ImageShowControl imageShowControl;
    private CompositeDisposable compositeDisposable;

    public ImageShowControlModule(ImageShowControl imageShowControl, CompositeDisposable compositeDisposable) {
        this.imageShowControl = imageShowControl;
        this.compositeDisposable = compositeDisposable;
    }

    @Provides
    ImageShowControlHelper provideImageShowControlHelper(RxBus rxBus) {
        return new ImageShowControlHelper(rxBus, imageShowControl, compositeDisposable);
    }
}

