package com.yan.campusbbs.module.campusbbs.job;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapterModule;
import com.yan.campusbbs.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , PagerTabAdapterModule.class
        , JobFragmentModule.class
}
)
public interface JobComponent {
    void inject(JobFragment jobFragment);
}
