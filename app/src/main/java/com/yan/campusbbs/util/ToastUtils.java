package com.yan.campusbbs.util;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/2/8.
 */

public class ToastUtils {
    private Toast toast;
    private Context context;

    @Inject
    public ToastUtils(Context context) {
        this.context = context;
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public void showShort(String str) {
        toast.setText(str);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showLong(String str) {
        toast.setText(str);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    public Disposable showUIShort(String str) {
      return   Observable.just(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    toast.setText(str);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                });
    }
}
