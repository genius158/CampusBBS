package com.yan.campusbbs.util;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;

import javax.inject.Inject;


/**
 * Created by Administrator on 2017/2/13.
 */

public class AnimationUtils {
    private static AnimationUtils animationUtils;

    public static AnimationUtils getInstance() {
        if (animationUtils == null) {
            synchronized (AnimationUtils.class) {
                if (animationUtils == null) {
                    animationUtils = new AnimationUtils();
                }
            }
        }
        return animationUtils;
    }

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
            final View target
            , final AnimationType type
            , long during
            , Interpolator interpolator
            , AnimatorListenerAdapter animatorListenerAdapter
            , final float[] outputValue
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
            case ALPHA:
                animator = ObjectAnimator.ofFloat(target, "alpha", values);
                animator.setInterpolator(interpolator);
                break;
            case TRANSLATEX:
                animator = ObjectAnimator.ofFloat(target, "translationX", values);
                animator.setInterpolator(interpolator);
                break;
            case SCALE:
                animator = ValueAnimator.ofFloat(values);
                animator.setInterpolator(interpolator);
                break;
        }
        animator.setDuration(during);

        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (outputValue != null) {
                            outputValue[0] = (float) animation.getAnimatedValue();
                            if (type == AnimationType.SCALE) {
                                target.setScaleX(outputValue[0]);
                                target.setScaleY(outputValue[0]);
                            }
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
