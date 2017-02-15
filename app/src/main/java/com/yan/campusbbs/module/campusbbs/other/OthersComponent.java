package com.yan.campusbbs.module.campusbbs.other;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        OthersFragmentModule.class
}
)
public interface OthersComponent {
    void inject(OthersFragment othersFragment);
}
