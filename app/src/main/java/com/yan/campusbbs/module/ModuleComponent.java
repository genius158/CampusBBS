package com.yan.campusbbs.module;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.module.filemanager.FileManagerPresenterModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenterModule;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.util.setting.SettingModule;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        FileManagerPresenterModule.class
        , SelfCenterPresenterModule.class
        , SettingModule.class

}
)
public interface ModuleComponent {
    void inject(MainActivity mainActivity);
}
