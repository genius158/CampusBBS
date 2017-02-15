package com.yan.campusbbs.module.campusbbs;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yan.campusbbs.R;
import com.yan.campusbbs.module.AppBarBehavior;

/**
 * Created by yan on 2017/2/8.
 */

public class CampusAppBarBehavior extends AppBarBehavior {

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
}