package com.yan.campusbbs;

import com.yan.campusbbs.base.ActivityScoped;
import com.yan.campusbbs.util.skin.ChangeSkinModule;

import dagger.Component;

@ActivityScoped
@Component(dependencies = ApplicationComponent.class
        , modules = ChangeSkinModule.class)
public interface FlashComponent {
    void inject(FlashActivity mainActivity);
}
