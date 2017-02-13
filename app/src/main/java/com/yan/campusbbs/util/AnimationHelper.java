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
        ROTATE,
    }

    public void start(int index, View target, AnimationType type, long during, Interpolator interpolator, float... values) {
        ValueAnimator animator = animations.get(index);
        if (animations.get(index) != null) {
            animator.setFloatValues(values);
            animator.start();
        } else {
            createAnimation(index, target, type, during, interpolator, values).start();
        }
    }


    public void start(int index, View target, AnimationType type, long during, Interpolator interpolator
            , AnimatorListenerAdapter animatorListenerAdapter, float... values) {
        ValueAnimator animator = animations.get(index);
        if (animations.get(index) != null) {
            animator.setFloatValues(values);
            animator.start();
        } else {
            ValueAnimator valueAnimator = createAnimation(index, target, type, during, interpolator, values);
            valueAnimator.addListener(animatorListenerAdapter);
            valueAnimator.start();
        }
    }

    private ValueAnimator createAnimation(int index, View target, AnimationType type, long during, Interpolator interpolator, float... values) {
        if (animations.get(index) != null) {
            return animations.get(index);
        }

        switch (type) {
            case TRANSLATEY:
                ObjectAnimator animator = ObjectAnimator.ofFloat(target, "translationY", values)
                        .setDuration(during);
                animator.setInterpolator(interpolator);
                animations.append(index, animator);
                return animator;
        }
        return null;
    }

}
