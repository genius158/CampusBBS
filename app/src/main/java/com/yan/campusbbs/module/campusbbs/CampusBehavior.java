package com.yan.campusbbs.module.campusbbs;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yan.campusbbs.R;

/**
 * Created by yan on 2017/2/8.
 */

public class CampusBehavior extends CoordinatorLayout.Behavior<View> {
    Context context;
    private int during = 200;
    private ObjectAnimator objectAnimatorShow;
    private ObjectAnimator objectAnimatorHide;

    private float barPosition;

    private boolean isShow = true;

    public CampusBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        offset(child, dyConsumed);
    }

    public void offset(View child, int dy) {
        if (dy < 0) {
            if (!isShow)
                show(child);
        } else if (dy > 0) {
            if (isShow)
                hide(child);
        }
    }

    private void show(View child) {
        if (isShow) return;
        if (objectAnimatorShow == null) {
            objectAnimatorShow = ObjectAnimator.ofFloat(child, "y", barPosition, 0)
                    .setDuration(during);
            objectAnimatorShow.addUpdateListener(valueAnimator -> {
                barPosition = (float) valueAnimator.getAnimatedValue();
            });
        }
        objectAnimatorShow.setFloatValues(barPosition, 0);
        if (objectAnimatorHide != null) {
            objectAnimatorHide.cancel();
        }
        objectAnimatorShow.start();
        isShow = true;
    }

    private void hide(View child) {
        if (!isShow) return;
        if (objectAnimatorHide == null) {
            objectAnimatorHide = ObjectAnimator.ofFloat(child, "y", barPosition
                    , -context.getResources().getDimension(R.dimen.action_bar_height))
                    .setDuration(during);
            objectAnimatorHide.addUpdateListener(valueAnimator -> {
                barPosition = (float) valueAnimator.getAnimatedValue();
            });
        }
        objectAnimatorHide.setFloatValues(barPosition
                , -context.getResources().getDimension(R.dimen.action_bar_height));
        if (objectAnimatorShow != null) {
            objectAnimatorShow.cancel();
        }
        objectAnimatorHide.start();
        isShow = false;
    }

}