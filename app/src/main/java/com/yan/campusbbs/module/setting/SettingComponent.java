package com.yan.campusbbs.module.setting;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.MainActivity;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
}
)
public interface SettingComponent {
    void inject(SettingActivity settingActivity);
}
