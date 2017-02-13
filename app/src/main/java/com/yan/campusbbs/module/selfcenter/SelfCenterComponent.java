package com.yan.campusbbs.module.selfcenter;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.AppBarHelperModule;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        AppBarHelperModule.class,
        SelfCenterModule.class,
        SettingModule.class,
}
)
public interface SelfCenterComponent {
    void inject(SelfCenterFragment selfCenterFragment);
}
