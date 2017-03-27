package com.yan.campusbbs.module.selfcenter.ui.register;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , RegisterModule.class
}
)
public interface RegisterComponent {
    void inject(RegisterActivity registerActivity);
}
