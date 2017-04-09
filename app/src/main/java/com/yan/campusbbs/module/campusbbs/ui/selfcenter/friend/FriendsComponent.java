package com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend.FriendModule;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend.FriendsActivity;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , FriendModule.class
}
)
public interface FriendsComponent {
    void inject(FriendsActivity friendsActivity);
}
