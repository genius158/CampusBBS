package com.yan.campusbbs;

import android.app.Application;
import android.content.Context;

import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.util.SPUtils;

import javax.inject.Inject;

import cn.smssdk.SMSSDK;
import imsdk.data.IMSDK;

public class ApplicationCampusBBS extends Application {
    public static ApplicationCampusBBS campusBBS;

    private static final String IM_APP_KEY="277a62910b19c827677b6613";

    @Inject
    SPUtils spUtils;

    private ApplicationComponent applicationComponent;

    public static ApplicationCampusBBS getApplication() {
        return campusBBS;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        campusBBS = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
        applicationComponent.inject(this);
    }

    private void init() {
        IMSDK.init(getApplicationContext(), IM_APP_KEY);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public String getSessionId() {
        return spUtils.getString(Context.MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                , SharedPreferenceConfig.SESSION_ID);
    }
}
