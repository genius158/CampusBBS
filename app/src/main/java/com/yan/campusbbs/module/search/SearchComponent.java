package com.yan.campusbbs.module.search;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class,
        SearchModule.class,
}
)
public interface SearchComponent {
    void inject(SearchActivity searchActivity);
}
