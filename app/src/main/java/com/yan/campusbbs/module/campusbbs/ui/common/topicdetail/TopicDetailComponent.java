package com.yan.campusbbs.module.campusbbs.ui.common.topicdetail;

import com.yan.campusbbs.ApplicationComponent;
import com.yan.campusbbs.base.ScopedFragment;
import com.yan.campusbbs.module.setting.SettingModule;

import dagger.Component;

@ScopedFragment
@Component(
        dependencies = ApplicationComponent.class
        , modules = {
        SettingModule.class
        , TopicDetailModule.class
}
)
public interface TopicDetailComponent {
    void inject(TopicDetailActivity detailActivity);
}
