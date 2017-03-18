package com.yan.campusbbs.module.campusbbs.ui.publish;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , PublishModule.class
}
)
public interface PublishComponent {
    void inject(PublishActivity friendsActivity);
}
