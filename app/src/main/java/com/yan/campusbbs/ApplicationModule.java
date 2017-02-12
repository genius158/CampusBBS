package com.yan.campusbbs;

import android.content.Context;

import com.yan.campusbbs.setting.AdapterImageControl;
import com.yan.campusbbs.setting.ImageControl;
import com.yan.campusbbs.repository.AppRetrofit;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * {@link
 */
@Module
public final class ApplicationModule {
    private final Context mContext;
    private final SPUtils spUtils;
    private final ImageControl imageControl;
    private final ACache aCache;

    ApplicationModule(Context context) {
        mContext = context;
        spUtils = new SPUtils(context);
        imageControl = new ImageControl(spUtils, context);
        aCache = ACache.get(context);
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Singleton
    @Provides
    ToastUtils provideToastUtil() {
        return new ToastUtils(mContext);
    }

    @Singleton
    @Provides
    RxBus provideRxBus() {
        return new RxBus();
    }

    @Singleton
    @Provides
    AppRetrofit provideAppRetrofit() {
        return new AppRetrofit();
    }

    @Singleton
    @Provides
    AdapterImageControl provideAdapterImageControl() {
        return new AdapterImageControl(imageControl);
    }

    @Provides
    ImageControl provideImageControl() {
        return imageControl;
    }

    @Provides
    SPUtils provideSPUtils() {
        return spUtils;
    }

    @Provides
    ACache provideACache() {
        return aCache;
    }
}