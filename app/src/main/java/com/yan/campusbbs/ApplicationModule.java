package com.yan.campusbbs;

import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yan.campusbbs.util.AppRetrofit;
import com.yan.campusbbs.util.RxBus;
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

    ApplicationModule(Context context) {
        mContext = context;
        Fresco.initialize(context);
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
}