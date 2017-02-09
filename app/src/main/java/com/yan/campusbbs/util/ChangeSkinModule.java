package com.yan.campusbbs.util;

import android.view.View;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/2/9.
 */

@Module
public class ChangeSkinModule {
    private final IChangeSkin iChangeSkin;
    private CompositeDisposable compositeDisposable;

    public ChangeSkinModule(IChangeSkin iChangeSkin, CompositeDisposable compositeDisposable) {
        this.iChangeSkin = iChangeSkin;
        this.compositeDisposable = compositeDisposable;
    }

    @Provides
    ChangeSkinHelper provideChangeSkinHelper(RxBus rxBus) {
        return new ChangeSkinHelper(rxBus, iChangeSkin, compositeDisposable);
    }
}

