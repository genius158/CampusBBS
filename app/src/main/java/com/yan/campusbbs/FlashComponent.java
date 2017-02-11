package com.yan.campusbbs;

import com.yan.campusbbs.base.ScopedActivity;
import com.yan.campusbbs.setting.SettingModule;

import dagger.Component;

@ScopedActivity
@Component(dependencies = ApplicationComponent.class
        , modules = SettingModule.class)
public interface FlashComponent {
    void inject(FlashActivity mainActivity);
}
