package com.yan.campusbbs.module.filemanager;

import android.content.Context;

import javax.inject.Inject;

public final class FileManagerPresenter implements FileManagerContract.Presenter {
    private FileManagerContract.View view;
    private Context context;

    @Inject
    FileManagerPresenter(Context context, FileManagerContract.View view) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void start() {

    }
}
