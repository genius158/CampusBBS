package com.yan.campusbbs.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by yan on 2016/8/25.
 */
public abstract class CommonPopupWindow {

    protected PopupWindow popupWindow;//弹窗
    protected View popupWindowView;
    protected View parentView;
    protected Context context;
    protected OnDismissFinishListener onDismissFinishListener;


    public CommonPopupWindow(View pView, int layoutRes, int widthPx, int widthPy, int animationRes) {
        parentView = pView;
        this.context = parentView.getContext();
        popupWindowView = LayoutInflater.from(context).inflate(layoutRes, null);
        viewInit();

        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView,
                widthPx,
                widthPy,
                true
        );
        if (animationRes != -1) {
            popupWindow.setAnimationStyle(animationRes);
        }

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setClippingEnabled(false);
        //菜单背景色
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissFinishListener != null) {
                    parentView.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            onDismissFinishListener.OnDismissFinish();
                        }
                    }, 450);
                }
            }
        });
    }

    public CommonPopupWindow(View pView, int layoutRes, int widthPx) {
        this(pView, layoutRes, widthPx, ViewGroup.LayoutParams.MATCH_PARENT, -1);
    }

    public CommonPopupWindow(View pView, int layoutRes) {
        this(pView, layoutRes, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, -1);
    }

    public void show() {
        popupWindow.showAtLocation(parentView, Gravity.RIGHT, 0, 0);
    }

    protected View findViewById(int idView) {
        return popupWindowView.findViewById(idView);
    }

    protected void setOnFocusListener(int idView, View.OnFocusChangeListener onFocusChangeListener) {
        View view = findViewById(idView);
        view.setFocusable(true);
        view.setOnFocusChangeListener(onFocusChangeListener);
    }

    /**
     * 控件的相关初始化设置
     */
    public abstract void viewInit();

    protected void setOnClickListener(int idView, View.OnClickListener onClickListener) {
        findViewById(idView).setOnClickListener(onClickListener);
    }

    public void setText(int idView, String text) {
        if (popupWindowView.findViewById(idView) instanceof TextView) {
            ((TextView) findViewById(idView)).setText(text);
        }
    }

    public void setImage(int idView, String url) {

    }

    public void setImage(int idView, int res) {
    }

    /**
     * 弹窗消失
     */
    public void dismiss() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 设置监听
     *
     * @param onDismissFinishListener dismiss完成监听
     */
    public void setOnDismissFinishListener(OnDismissFinishListener onDismissFinishListener) {
        this.onDismissFinishListener = onDismissFinishListener;
    }

    /**
     * dismiss结束监听
     */
    public interface OnDismissFinishListener {
        void OnDismissFinish();
    }

}
