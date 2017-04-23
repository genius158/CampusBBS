//package com.yan.campusbbs.module.common;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.WindowManager;
//
//import com.yan.campusbbs.R;
//
///**
// * Created by yan on 2017/4/14.
// */
//
//public class LoadingDialog {
//    private static volatile LoadingDialog loadingDialog;
//    private View contentView;
//    private Context context;
//    private WindowManager windowManager;
//    private WindowManager.LayoutParams layoutParams;
//
//    private LoadingDialog(Context context) {
//        this.context = context.getApplicationContext();
//        contentView = LayoutInflater.from(this.context).inflate(
//                R.layout.dialog_loading, null);
//
//        windowManager = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
//        layoutParams = new WindowManager.LayoutParams();
//        layoutParams.height = -1;
//        layoutParams.width = -1;
//        layoutParams.format = 1;
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
//        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//    }
//
//    public void show() {
//        windowManager.addView(contentView, layoutParams);
//    }
//
//    public void hide() {
//        windowManager.removeView(contentView);
//    }
//
//    public static LoadingDialog getInstance(Context context) {
//        if (loadingDialog == null) {
//            synchronized (LoadingDialog.class) {
//                if (loadingDialog == null) {
//                    loadingDialog = new LoadingDialog(context);
//                }
//            }
//        }
//        return loadingDialog;
//    }
//
//
//}
