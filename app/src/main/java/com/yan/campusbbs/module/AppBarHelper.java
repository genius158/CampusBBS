package com.yan.campusbbs.module;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import com.yan.campusbbs.R;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/2/9.
 */

public class AppBarHelper {
    Context context;
    View appBar;
    private int during = 200;
    private ObjectAnimator objectAnimatorShow;
    private ObjectAnimator objectAnimatorHide;

    private float barPosition;
    private boolean isShow = true;

    @Inject
    public AppBarHelper(Context context, View appBar) {
        this.context = context;
        this.appBar = appBar;
    }

    public void offset(int dy) {
        if (dy < 0) {
            show();
        } else if (dy > 0) {
            hide();
        }
    }


    public void show() {
        if (isShow) return;
        if (objectAnimatorShow == null) {
            objectAnimatorShow = ObjectAnimator.ofFloat(appBar, "y", barPosition, 0)
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

    public void hide() {
        if (!isShow) return;
        if (objectAnimatorHide == null) {
            objectAnimatorHide = ObjectAnimator.ofFloat(appBar, "y", barPosition
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
