package com.yan.campusbbs.util;

import android.util.SparseArray;
import android.view.View;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/2/13.
 */

public class AnimationHelper {
    private SparseArray<ValueAnimator> animations;

    @Inject
    public AnimationHelper() {
        animations = new SparseArray<>();
    }


    public boolean start(int index) {
        if (animations.get(index) != null) {
            animations.get(index).start();
            return true;
        } else {
            return false;
        }
    }


    public enum AnimationType {
        ALPHA,
        SCALEX,
        SCALEY,
        TRANSLATEY,
        TRANSLATEX,
        ROTATION,
    }

    public void start(int index, View target, AnimationType type, long during, Interpolator interpolator, float[] outputValue, float... values) {
        ValueAnimator animator = animations.get(index);
        if (animations.get(index) != null) {
            animator.setFloatValues(values);
            animator.start();
        } else {
            createAnimation(index, target, type, during, interpolator, null, outputValue, values).start();
        }
    }


    public void start(int index, View target, AnimationType type, long during, Interpolator interpolator
            , AnimatorListenerAdapter animatorListenerAdapter, float[] outputValue, float... values) {
        ValueAnimator animator = animations.get(index);
        if (animations.get(index) != null) {
            animator.setFloatValues(values);
            animator.start();
        } else {
            ValueAnimator valueAnimator =
                    createAnimation(index, target, type, during, interpolator, animatorListenerAdapter, outputValue, values);
            valueAnimator.start();
        }
    }

    public ValueAnimator createAnimation(int index
            , View target
            , AnimationType type
            , long during
            , Interpolator interpolator
            , AnimatorListenerAdapter animatorListenerAdapter
            , float[] outputValue
            , float... values) {
        if (animations.get(index) != null) {
            return animations.get(index);
        }
        ObjectAnimator animator = null;
        switch (type) {
            case ROTATION:
                animator = ObjectAnimator.ofFloat(target, "rotation", values);
                animator.setInterpolator(interpolator);
                break;
            case TRANSLATEY:
                animator = ObjectAnimator.ofFloat(target, "translationY", values);
                animator.setInterpolator(interpolator);
                break;
        }
        animator.setDuration(during);
        animator.addUpdateListener(
                animation -> {
                    if (outputValue != null)
                        outputValue[0] = (float) animation.getAnimatedValue();
                }
        );
        if (animatorListenerAdapter != null) {
            animator.addListener(animatorListenerAdapter);
        }
        animations.append(index, animator);
        return animator;
    }

}
