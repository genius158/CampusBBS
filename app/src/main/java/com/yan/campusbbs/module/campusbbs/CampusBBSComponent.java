package com.yan.campusbbs.module.campusbbs;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.module.campusbbs.study.StudyPresenterModule;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.util.ChangeSkinModule;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        StudyPresenterModule.class
        , ChangeSkinModule.class
}
)
public interface CampusBBSComponent {
    void inject(CampusBBSFragment campusBBSFragment);
}
