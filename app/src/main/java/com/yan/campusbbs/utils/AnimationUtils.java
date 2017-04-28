package com.yan.campusbbs.utils;

import android.view.View;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/2/13.
 */

public class AnimationUtils {

    @Inject
    public AnimationUtils() {
    }

    public enum AnimationType {
        ALPHA,
        SCALEX,
        SCALEY,
        SCALE,
        TRANSLATEY,
        TRANSLATEX,
        ROTATION,
    }

    public ValueAnimator createAnimation(
            View target
            , AnimationType type
            , long during
            , Interpolator interpolator
            , AnimatorListenerAdapter animatorListenerAdapter
            , float[] outputValue
            , float... values) {

        ValueAnimator animator = null;
        switch (type) {
            case ROTATION:
                animator = ObjectAnimator.ofFloat(target, "rotation", values);
                animator.setInterpolator(interpolator);
                break;
            case TRANSLATEY:
                animator = ObjectAnimator.ofFloat(target, "translationY", values);
                animator.setInterpolator(interpolator);
                break;
            case SCALE:
                animator = ValueAnimator.ofFloat(values);
                animator.setInterpolator(interpolator);
                break;
        }
        animator.setDuration(during);

        animator.addUpdateListener(
                animation -> {
                    if (outputValue != null) {
                        outputValue[0] = (float) animation.getAnimatedValue();
                        if (type == AnimationType.SCALE) {
                            target.setScaleX(outputValue[0]);
                            target.setScaleY(outputValue[0]);
                        }
                    }
                }
        );
        if (animatorListenerAdapter != null) {
            animator.addListener(animatorListenerAdapter);
        }
        return animator;
    }

}
