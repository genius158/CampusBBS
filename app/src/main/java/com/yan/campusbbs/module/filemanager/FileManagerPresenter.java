package com.yan.campusbbs.module.filemanager;

import android.content.Context;

import com.yan.campusbbs.module.filemanager.api.FileApi;
import com.yan.campusbbs.utils.AppRetrofit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class FileManagerPresenter implements FileManagerContract.Presenter {
    private FileManagerContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    FileManagerPresenter(Context context, FileManagerContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
    }


    @Override
    public void start() {

    }

    @Override
    public void getVideo() {
        FileApi fileApi = appRetrofit.retrofit().create(FileApi.class);
        view.addDisposable(fileApi.getVideo().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responseBody -> {
            view.setVideo(responseBody);
        },throwable -> {throwable.printStackTrace();
        view.error();}));
    }

    @Override
    public void getImages(int pageNo) {
        FileApi fileApi = appRetrofit.retrofit().create(FileApi.class);
        view.addDisposable(fileApi.getImages(pageNo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    view.setImages(responseBody);
                },throwable -> {throwable.printStackTrace();
                    view.error();}));
    }
}
