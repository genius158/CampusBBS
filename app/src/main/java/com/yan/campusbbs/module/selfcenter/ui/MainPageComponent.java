package com.yan.campusbbs.module.selfcenter.ui;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.selfcenter.ui.register.RegisterActivity;
import com.yan.campusbbs.module.selfcenter.ui.register.RegisterModule;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
}
)
public interface MainPageComponent {
    void inject(MainPageFragment mainPageFragment);
}
