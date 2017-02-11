package com.yan.campusbbs.module.campusbbs.study;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapterModule;
import com.yan.campusbbs.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class,
        StudyFragmentModule.class,
        PagerTabAdapterModule.class
}
)
public interface StudyComponent {
    void inject(StudyFragment studyFragment);
}
