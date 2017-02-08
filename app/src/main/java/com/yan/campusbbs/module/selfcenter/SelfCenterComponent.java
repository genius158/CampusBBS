package com.yan.campusbbs.module.selfcenter;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.module.AppBarHelperModule;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        AppBarHelperModule.class
}
)
public interface SelfCenterComponent {
    void inject(SelfCenterFragment selfCenterFragment);
}
