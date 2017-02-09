package com.yan.campusbbs.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by yan on 2017/2/4.
 */

public class DispatchEventFrameLayout extends FrameLayout {
    private OnDispatchEvent onDispatchEvent;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (onDispatchEvent != null) {
            onDispatchEvent.onDispatch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public DispatchEventFrameLayout(Context context) {
        super(context);
    }

    public DispatchEventFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchEventFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setOnDispatchEvent(OnDispatchEvent onDispatchEvent) {
        this.onDispatchEvent = onDispatchEvent;
    }

}
