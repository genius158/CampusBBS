package com.yan.campusbbs.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by yan on 2017/2/4.
 */

public class DispatchEventViewPager extends ViewPager {
    private OnDispatchEvent onDispatchEvent;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (onDispatchEvent != null) {
            onDispatchEvent.onDispatch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public DispatchEventViewPager(Context context) {
        super(context);
    }

    public DispatchEventViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public void setOnDispatchEvent(OnDispatchEvent onDispatchEvent) {
        this.onDispatchEvent = onDispatchEvent;
    }

}
