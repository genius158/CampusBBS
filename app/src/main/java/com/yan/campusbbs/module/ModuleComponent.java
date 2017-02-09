package com.yan.campusbbs.module;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.module.filemanager.FileManagerPresenterModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenterModule;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.util.ChangeSkinModule;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        FileManagerPresenterModule.class
        , SelfCenterPresenterModule.class
        , ChangeSkinModule.class

}
)
public interface ModuleComponent {
    void inject(MainActivity mainActivity);
}
