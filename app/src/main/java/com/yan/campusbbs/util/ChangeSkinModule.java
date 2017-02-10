package com.yan.campusbbs.util;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/2/9.
 */

@Module
public class ChangeSkinModule {
    private final ChangeSkin changeSkin;
    private CompositeDisposable compositeDisposable;

    public ChangeSkinModule(ChangeSkin changeSkin, CompositeDisposable compositeDisposable) {
        this.changeSkin = changeSkin;
        this.compositeDisposable = compositeDisposable;
    }

    @Provides
    ChangeSkinHelper provideChangeSkinHelper(RxBus rxBus) {
        return new ChangeSkinHelper(rxBus, changeSkin, compositeDisposable);
    }
}

