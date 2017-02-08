package com.yan.campusbbs;

import com.yan.campusbbs.base.ActivityScoped;

import dagger.Component;

@ActivityScoped
@Component(dependencies = ApplicationComponent.class)
public interface FlashComponent {
    void inject(FlashActivity mainActivity);
}
