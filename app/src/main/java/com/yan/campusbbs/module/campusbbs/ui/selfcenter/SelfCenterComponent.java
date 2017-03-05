package com.yan.campusbbs.module.campusbbs.ui.selfcenter;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.setting.SettingActivity;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
}
)
public interface SelfCenterComponent {
    void inject(SelfCenterActivity settingActivity);
}
