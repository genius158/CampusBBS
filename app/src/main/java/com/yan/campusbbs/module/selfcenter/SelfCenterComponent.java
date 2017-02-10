package com.yan.campusbbs.module.selfcenter;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.module.AppBarHelperModule;
import com.yan.campusbbs.util.imagecontrol.ImageShowControlModule;
import com.yan.campusbbs.util.skin.ChangeSkinModule;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        AppBarHelperModule.class,
        SelfCenterModule.class,
        ImageShowControlModule.class,
        ChangeSkinModule.class
}
)
public interface SelfCenterComponent {
    void inject(SelfCenterFragment selfCenterFragment);
}
