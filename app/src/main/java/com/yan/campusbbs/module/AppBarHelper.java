package com.yan.campusbbs.module;

import android.animation.ObjectAnimator;
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
    private View appBar;
    private int during = 200;
    private ObjectAnimator objectAnimatorShow;
    private ObjectAnimator objectAnimatorHide;

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
        this.appBar = appBar;
    }

    public AppBarHelper() {
    }


    public void offset(int dy) {
        if (dy < 0) {
            show();
        } else if (dy > 0) {
            hide();
        }
    }

    public void show() {
        if (isShow || appBar == null) return;

        if (objectAnimatorShow == null) {
            objectAnimatorShow = ObjectAnimator.ofFloat(appBar, "y", barPosition, 0 + height)
                    .setDuration(during);
            objectAnimatorShow.addUpdateListener(valueAnimator -> {
                barPosition = (float) valueAnimator.getAnimatedValue();
            });
        }
        objectAnimatorShow.setFloatValues(barPosition, 0 + height);
        if (objectAnimatorHide != null) {
            objectAnimatorHide.cancel();
        }
        objectAnimatorShow.start();
        isShow = true;
    }

    public void hide() {
        if (!isShow || appBar == null) return;
        if (objectAnimatorHide == null) {
            objectAnimatorHide = ObjectAnimator.ofFloat(appBar, "y", barPosition
                    , (startY != -1000)
                            ? startY
                            : -context.getResources().getDimension(R.dimen.action_bar_height))
                    .setDuration(during);
            objectAnimatorHide.addUpdateListener(valueAnimator -> {
                barPosition = (float) valueAnimator.getAnimatedValue();
            });
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

    public void setBar(Context context, View appBar) {
        this.context = context;
        this.appBar = appBar;
    }

    public void setTagHide(Context context) {
        isShow = false;
        barPosition = (startY != -1000) ? startY
                : -context.getResources().getDimension(R.dimen.action_bar_height);

    }
}
