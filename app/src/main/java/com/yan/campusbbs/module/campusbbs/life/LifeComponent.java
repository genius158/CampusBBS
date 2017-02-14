package com.yan.campusbbs.module.campusbbs.life;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.campusbbs.common.CampusTabPagerModule;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , LifeFragmentModule.class
        , CampusTabPagerModule.class

}
)
public interface LifeComponent {
    void inject(LifeFragment lifeFragment);
}
