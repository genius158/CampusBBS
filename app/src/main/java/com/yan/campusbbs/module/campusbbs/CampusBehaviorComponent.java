package com.yan.campusbbs.module.campusbbs;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
)
public interface CampusBehaviorComponent {
    void inject(CampusBehavior behavior);
}
