package com.yan.campusbbs.util.setting;

import com.yan.campusbbs.util.RxBus;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/2/9.
 */

@Module
public class SettingModule {
    private SystemSetting systemSetting;
    private CompositeDisposable compositeDisposable;

    public SettingModule(SystemSetting systemSetting
            , CompositeDisposable compositeDisposable) {
        this.systemSetting = systemSetting;
        this.compositeDisposable = compositeDisposable;
    }

    @Provides
    SettingHelper provideSettingHelper(RxBus rxBus) {
        return new SettingHelper(rxBus, systemSetting, compositeDisposable);
    }

}

