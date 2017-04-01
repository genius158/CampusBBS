package com.yan.campusbbs.module.campusbbs.ui.userinfo;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.campusbbs.ui.publish.PublishActivity;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , UserInfoModule.class
}
)
public interface UserInfoComponent {
    void inject(UserInfoActivity userInfoActivity);
}
