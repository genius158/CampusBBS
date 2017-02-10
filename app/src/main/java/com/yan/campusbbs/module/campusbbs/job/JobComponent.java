package com.yan.campusbbs.module.campusbbs.job;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapterModule;
import com.yan.campusbbs.util.setting.SettingModule;

import dagger.Component;

@FragmentScoped
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
