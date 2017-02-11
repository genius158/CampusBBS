package com.yan.campusbbs.module;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.module.filemanager.FileManagerPresenterModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenterModule;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.setting.SettingModule;

import dagger.Component;

@ScopedFragment
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
