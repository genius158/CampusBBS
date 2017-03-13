package com.yan.campusbbs.module.campusbbs.ui.selfcenter.ui.chat;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , ChatModule.class
}
)
public interface ChatComponent {
    void inject(ChatActivity friendsActivity);
}
