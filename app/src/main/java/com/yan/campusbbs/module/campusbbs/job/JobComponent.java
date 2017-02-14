package com.yan.campusbbs.module.campusbbs.job;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.campusbbs.CampusTabPagerModule;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , JobFragmentModule.class
        , CampusTabPagerModule.class
}
)
public interface JobComponent {
    void inject(JobFragment jobFragment);
}
