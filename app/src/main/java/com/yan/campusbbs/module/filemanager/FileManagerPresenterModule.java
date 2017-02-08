package com.yan.campusbbs.module.filemanager;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link FileManagerPresenter}.
 */
@Module
public class FileManagerPresenterModule {

    private final FileManagerContract.View mView;

    public FileManagerPresenterModule(FileManagerContract.View view ) {
        mView = view;
    }

    @Provides
    FileManagerContract.View provideFileManagerContractView() {
        return mView;
    }

}
