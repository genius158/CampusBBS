package com.yan.campusbbs.module.campusbbs;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yan.campusbbs.R;
import com.yan.campusbbs.module.AppBarBehavior;
import com.yan.campusbbs.rxbusaction.ActionFloatingButton;
import com.yan.campusbbs.util.RxBus;

/**
 * Created by yan on 2017/2/8.
 */

public class CampusAppBarBehavior extends AppBarBehavior {
    private RxBus rxBus;
    private ActionFloatingButton actionFloating;

    public CampusAppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (target.getId() == R.id.pager_bar_more_recycler) {
            return false;
        } else {
            return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

    }

    public void setRxBus(RxBus rxBus) {
        this.rxBus = rxBus;
    }

    @Override
    public void show() {
        super.show();
        if (actionFloating == null) {
            actionFloating = new ActionFloatingButton();
        }
        actionFloating.isScrollDown = true;
        rxBus.post(actionFloating);
    }
}