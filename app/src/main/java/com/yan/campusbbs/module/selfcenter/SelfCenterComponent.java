package com.yan.campusbbs.module.selfcenter;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.module.MainActivity;
import com.yan.campusbbs.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(dependencies = ApplicationComponent.class,
        modules = SelfCenterPresenterModule.class)
public interface SelfCenterComponent {
    void inject(MainActivity mainActivity);
}
