package com.yan.campusbbs.module.common;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yan.campusbbs.R;

/**
 * Created by yan on 2017/4/14.
 */

public class LoadingDialog extends Dialog {
    private static volatile LoadingDialog loadingDialog;
    private View contentView;

    public LoadingDialog(Context context) {
        super(context);
        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        setContentView(contentView);
    }

    public static LoadingDialog getInstance(Context context) {
        if (loadingDialog == null) {
            synchronized (LoadingDialog.class) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(context);
                }
            }
        }
        return loadingDialog;
    }


}
