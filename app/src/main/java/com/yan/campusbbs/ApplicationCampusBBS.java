package com.yan.campusbbs;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

public class ApplicationCampusBBS extends MultiDexApplication {
    public static ApplicationCampusBBS campusBBS;

    @Inject
    SPUtils spUtils;
    @Inject
    RxBus rxBus;

    private ApplicationComponent applicationComponent;

    public static ApplicationCampusBBS getApplication() {
        return campusBBS;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        campusBBS = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
        applicationComponent.inject(this);
        init();
    }

    private void init() {
        ImManager.init(getApplicationContext(),rxBus);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public String getSessionId() {
        return spUtils.getString(Context.MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                , SharedPreferenceConfig.SESSION_ID);
    }
}
