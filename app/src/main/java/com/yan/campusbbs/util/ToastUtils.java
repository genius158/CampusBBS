package com.yan.campusbbs.util;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/2/8.
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
}
