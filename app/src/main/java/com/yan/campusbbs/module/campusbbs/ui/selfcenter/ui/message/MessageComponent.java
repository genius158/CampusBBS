package com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.message;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , MessageModule.class
}
)
public interface MessageComponent {
    void inject(MessageActivity friendsActivity);
}
