package com.yan.campusbbs.module.campusbbs.ui.publish.pic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;

public class MultiImageSelector {
    public static final String EXTRA_RESULT = "select_result";
    private boolean mShowCamera = true;
    private int mMaxCount = 9;
    private int mMode = 1;
    private ArrayList<String> mOriginData;
    private static MultiImageSelector sSelector;

    /**
     * @deprecated
     */
    @Deprecated
    private MultiImageSelector(Context context) {
    }

    private MultiImageSelector() {
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static MultiImageSelector create(Context context) {
        if (sSelector == null) {


            sSelector = new MultiImageSelector(context);
        }

        return sSelector;
    }

    public static MultiImageSelector create() {
        if (sSelector == null) {
            sSelector = new MultiImageSelector();
        }

        return sSelector;
    }

    public MultiImageSelector showCamera(boolean show) {
        this.mShowCamera = show;
        return sSelector;
    }

    public MultiImageSelector count(int count) {
        this.mMaxCount = count;
        return sSelector;
    }

    public MultiImageSelector single() {
        this.mMode = 0;
        return sSelector;
    }

    public MultiImageSelector multi() {
        this.mMode = 1;
        return sSelector;
    }

    public MultiImageSelector origin(ArrayList<String> images) {
        this.mOriginData = images;
        return sSelector;
    }

    public void start(Activity activity, int requestCode) {
        if (this.hasPermission(activity)) {
            activity.startActivityForResult(this.createIntent(activity), requestCode);
        } else {
            Toast.makeText(activity, "没有权限", Toast.LENGTH_SHORT).show();
        }

    }

    public void start(Fragment fragment, int requestCode) {
        Context context = fragment.getContext();
        if (this.hasPermission(context)) {
            fragment.startActivityForResult(this.createIntent(context), requestCode);
        } else {
            Toast.makeText(context, "没有权限", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean hasPermission(Context context) {
        return VERSION.SDK_INT >= 16 ? ContextCompat.checkSelfPermission(context, "android.permission.READ_EXTERNAL_STORAGE") == 0 : true;
    }

    private Intent createIntent(Context context) {
        Intent intent = new Intent(context, PicActivity.class);
        intent.putExtra("show_camera", this.mShowCamera);
        intent.putExtra("max_select_count", this.mMaxCount);
        if (this.mOriginData != null) {
            intent.putStringArrayListExtra("default_list", this.mOriginData);
        }

        intent.putExtra("select_count_mode", this.mMode);
        return intent;
    }
}
