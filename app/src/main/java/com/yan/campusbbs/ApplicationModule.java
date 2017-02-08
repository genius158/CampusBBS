package com.yan.campusbbs;

import android.content.Context;

import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.ToastUtil;

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
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Singleton
    @Provides
    ToastUtil provideToastUtil() {
        return new ToastUtil(mContext);
    }

    @Singleton
    @Provides
    RxBus provideRxBus() {
        return new RxBus();
    }
}