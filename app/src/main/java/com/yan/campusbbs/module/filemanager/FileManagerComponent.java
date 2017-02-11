package com.yan.campusbbs.module.filemanager;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
}
)
public interface FileManagerComponent {
    void inject(FileManagerFragment fileManagerFragment);
}
