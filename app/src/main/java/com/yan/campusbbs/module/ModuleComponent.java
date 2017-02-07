package com.yan.campusbbs.module;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.module.MainActivity;
import com.yan.campusbbs.module.campusbbs.CampusBBSPresenterModule;
import com.yan.campusbbs.module.filemanager.FileManagerPresenterModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenterModule;
import com.yan.campusbbs.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        FileManagerPresenterModule.class
        , SelfCenterPresenterModule.class
        , CampusBBSPresenterModule.class
}
)
public interface ModuleComponent {
    void inject(MainActivity mainActivity);
}
