package com.yan.campusbbs.module;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yan on 2017/2/8.
 */

public class AppBarBehavior extends CoordinatorLayout.Behavior<View> {
    private Context context;
    protected AppBarHelper appBarHelper;

    public boolean isShow() {
        return appBarHelper.isShow();
    }

    public AppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (appBarHelper == null) {
            appBarHelper = new AppBarHelper(context, child);
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != -1;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        appBarHelper.offset(dyConsumed);
    }

    public void show() {
        if (appBarHelper != null) {
            appBarHelper.show();
        }
    }
}