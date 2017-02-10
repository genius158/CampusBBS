package com.yan.campusbbs.module.selfcenter;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.module.AppBarHelperModule;
import com.yan.campusbbs.util.ChangeSkinModule;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        AppBarHelperModule.class,
        SelfCenterModule.class,
        ChangeSkinModule.class
}
)
public interface SelfCenterComponent {
    void inject(SelfCenterFragment selfCenterFragment);
}
