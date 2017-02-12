package com.yan.campusbbs.module;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yan on 2017/2/8.
 */

public class AppBarBehavior extends CoordinatorLayout.Behavior<View> {
    private Context context;
    protected AppBarHelper appBarHelper;
    private boolean needBarHelperSet = true;

    public boolean isShow() {
        return appBarHelper.isShow();
    }

    public void setTagHide() {
        appBarHelper.setTagHide();
    }

    public AppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        appBarHelper = new AppBarHelper();
    }


    public void setAppBar(View appBar) {
        needBarHelperSet = false;
        appBarHelper.addBar(context, appBar);
    }


    public void addAppBar(View appBar) {
        appBarHelper.addBar(context, appBar);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (needBarHelperSet) {
            needBarHelperSet = false;
            appBarHelper.addBar(context, child);
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

    public void hide() {
        if (appBarHelper != null) {
            appBarHelper.hide();
        }
    }

    public void setTagHide(Context context) {
        appBarHelper.setTagHide(context);
    }
}