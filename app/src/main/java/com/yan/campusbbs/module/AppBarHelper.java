package com.yan.campusbbs.module;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.yan.campusbbs.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/2/9.
 */

public class AppBarHelper {
    private Context context;
    private List<View> appBars;
    private int during = 200;
    private ValueAnimator objectAnimatorShow;
    private ValueAnimator objectAnimatorHide;

    private float barPosition;
    private boolean isShow = true;

    private float startY = -1000;
    private float height = 0;

    public void setBarStartYHeight(float startY, float height) {
        this.startY = startY;
        barPosition = startY;
        this.height = height;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setTagHide() {
        setTagHide(context);
    }

    @Inject
    public AppBarHelper(Context context, View appBar) {
        this.context = context;
        appBars = new ArrayList<>();
        appBars.add(appBar);
    }

    public AppBarHelper() {
        appBars = new ArrayList<>();
    }


    public void offset(int dy) {
        if (dy < 0) {
            show();
        } else if (dy > 0) {
            hide();
        }
    }

    public void show() {
        if (isShow || appBars.isEmpty()) return;

        if (objectAnimatorShow == null) {
            objectAnimatorShow = ObjectAnimator.ofFloat();
            objectAnimatorShow.addUpdateListener(animatorUpdateListener);
            objectAnimatorShow.setDuration(during);
        }
        objectAnimatorShow.setFloatValues(barPosition, height);
        if (objectAnimatorHide != null) {
            objectAnimatorHide.cancel();
        }
        objectAnimatorShow.start();
        isShow = true;
    }

    public void hide() {
        if (!isShow || appBars.isEmpty()) return;
        if (objectAnimatorHide == null) {
            objectAnimatorHide = ObjectAnimator.ofFloat();
            objectAnimatorHide.addUpdateListener(animatorUpdateListener);
            objectAnimatorHide.setDuration(during);
        }
        objectAnimatorHide.setFloatValues(barPosition
                , (startY != -1000)
                        ? startY
                        : -context.getResources().getDimension(R.dimen.action_bar_height));
        if (objectAnimatorShow != null) {
            objectAnimatorShow.cancel();
        }
        objectAnimatorHide.start();
        isShow = false;
    }

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = valueAnimator -> {
        barPosition = (float) valueAnimator.getAnimatedValue();
        for (View view : appBars) {
            view.setY(barPosition);
        }
    };

    public void addBar(Context context, View appBar) {
        this.context = context;
        this.appBars.add(appBar);
    }

    public void setTagHide(Context context) {
        isShow = false;
        barPosition = (startY != -1000) ? startY
                : -context.getResources().getDimension(R.dimen.action_bar_height);

    }
}
