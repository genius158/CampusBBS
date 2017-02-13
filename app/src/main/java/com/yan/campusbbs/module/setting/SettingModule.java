package com.yan.campusbbs.module.setting;

import com.yan.campusbbs.util.RxBus;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yan on 2017/2/9.
 */

@Module
public class SettingModule {
    private SettingControl settingControl;
    private CompositeDisposable compositeDisposable;

    public SettingModule(SettingControl settingControl
            , CompositeDisposable compositeDisposable) {
        this.settingControl = settingControl;
        this.compositeDisposable = compositeDisposable;
    }

    @Provides
    SettingHelper provideSettingHelper(RxBus rxBus) {
        return new SettingHelper(rxBus, settingControl, compositeDisposable);
    }

}

