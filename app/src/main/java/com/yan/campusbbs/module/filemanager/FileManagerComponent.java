package com.yan.campusbbs.module.filemanager;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.util.ChangeSkinModule;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        ChangeSkinModule.class
}
)
public interface FileManagerComponent {
    void inject(FileManagerFragment fileManagerFragment);
}
