package com.yan.campusbbs.module.campusbbs;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.rxbusaction.ActionCampusBBSFragmentFinish;
import com.yan.campusbbs.rxbusaction.ActionPagerToCampusBBS;
import com.yan.campusbbs.util.RxBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/2/8.
 */

public class CampusBehavior extends CoordinatorLayout.Behavior<View> {
    private Context context;
    private View child;

    private int during = 200;
    private ObjectAnimator objectAnimatorShow;
    private ObjectAnimator objectAnimatorHide;

    private float barPosition;

    private boolean isShow = true;

    @Inject
    RxBus rxBus;

    private CompositeDisposable compositeDisposable;

    public CampusBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        DaggerCampusBehaviorComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) context.getApplicationContext())
                        .getApplicationComponent())
                .build().inject(this);

        initRxBusDisposable();
    }

    private void initRxBusDisposable() {
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(rxBus
                .getEvent(ActionPagerToCampusBBS.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pagerToCampusBBS -> {
                    show();
                }));
        compositeDisposable.add(rxBus
                .getEvent(ActionCampusBBSFragmentFinish.class)
                .observeOn(Schedulers.io())
                .subscribe(pagerToCampusBBS -> {
                    if (!compositeDisposable.isDisposed()) {
                        compositeDisposable.dispose();
                    }
                }));
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        this.child = child;
        offset(dyConsumed);
    }

    public void offset(int dy) {
        if (dy < 0) {
            show();
        } else if (dy > 0) {
            hide();
        }
    }

    private void show() {
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

    private void hide() {
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