package com.yan.campusbbs.module.selfcenter.ui.mainpage;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.AppBarHelperModule;
import com.yan.campusbbs.module.selfcenter.ui.friendpage.FriendPageActivity;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SelfCenterModule.class,
        SettingModule.class,
}
)
public interface SelfCenterOtherComponent {
    void inject(FriendPageActivity friendPageActivity);
}
