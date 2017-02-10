package com.yan.campusbbs.module.campusbbs.life;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.FragmentScoped;
import com.yan.campusbbs.module.campusbbs.PagerTabAdapterModule;
import com.yan.campusbbs.util.skin.ChangeSkinModule;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        ChangeSkinModule.class
        , PagerTabAdapterModule.class
        , LifeFragmentModule.class
}
)
public interface LifeComponent {
    void inject(LifeFragment lifeFragment);
}
