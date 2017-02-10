package com.yan.campusbbs;

import com.yan.campusbbs.base.ActivityScoped;
import com.yan.campusbbs.util.setting.SettingModule;

import dagger.Component;

@ActivityScoped
@Component(dependencies = ApplicationComponent.class
        , modules = SettingModule.class)
public interface FlashComponent {
    void inject(FlashActivity mainActivity);
}
