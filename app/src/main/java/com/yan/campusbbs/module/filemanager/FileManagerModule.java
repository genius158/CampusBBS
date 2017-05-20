package com.yan.campusbbs.module.filemanager;

import android.content.Context;

import com.yan.campusbbs.utils.AppRetrofit;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link FileManagerPresenter}.
 */
@Module
public class FileManagerModule {

    private final FileManagerContract.View mView;

    public FileManagerModule(FileManagerContract.View view) {
        mView = view;
    }

    @Provides
    FileManagerPresenter provideFileManagerPresenter(Context context, AppRetrofit appRetrofit) {
        return new FileManagerPresenter(context, mView,appRetrofit);
    }

}
