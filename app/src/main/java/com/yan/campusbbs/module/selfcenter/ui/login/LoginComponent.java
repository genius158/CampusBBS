package com.yan.campusbbs.module.selfcenter.ui.login;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        LoginModule.class,
        SettingModule.class,
}
)
public interface LoginComponent {
    void inject(LogInFragment logInFragment);
}
