package com.yan.campusbbs;

import android.content.Context;

import com.yan.campusbbs.module.setting.AdapterImageControl;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.utils.AppRetrofit;
import com.yan.campusbbs.utils.RxBus;
import com.yan.campusbbs.utils.ToastUtils;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();

    RxBus getRxBus();

    ToastUtils getToastUtil();

    AdapterImageControl getAdapterImageControl();

    ImageControl getImageControl();

    AppRetrofit getAppRetrofit();

    void inject(ApplicationCampusBBS applicationCampusBBS);
}
