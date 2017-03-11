package com.yan.campusbbs.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by yan on 2017/3/11.
 */

public class AnimationGroupManager {
    private View target;
    private AnimationUtils.AnimationType animationType;
    private float[] animationValues;
    private float[] animationReversalValues;
    private long animationDuring = 200;
    private float[] animationCurrentValue;
    private ValueAnimator goAnimation;
    private ValueAnimator backAnimation;
    private Interpolator interpolator;
    private AnimatorListenerAdapter listenerAdapter;

    public AnimationGroupManager(View target, long animationDuring, AnimationUtils.AnimationType animationType, AnimatorListenerAdapter listenerAdapter, Interpolator interpolator, float... animationValues) {
        this.target = target;
        this.animationDuring = animationDuring;
        this.animationValues = animationValues;
        this.animationType = animationType;
        this.interpolator = interpolator;
        this.listenerAdapter = listenerAdapter;
        this.animationCurrentValue = new float[]{-1};
        this.animationReversalValues = new float[this.animationValues.length];
        for (int i = 0; i < this.animationValues.length; i++) {
            animationReversalValues[i] = this.animationValues[this.animationValues.length - i - 1];
        }
    }

    public void show() {
        if (animationCurrentValue[0]
                == animationValues[animationValues.length - 1]
                && animationCurrentValue[0] != -1
                ) {
            return;
        }
        if (target.getVisibility() != View.VISIBLE)
            target.setVisibility(View.VISIBLE);

        if (backAnimation.isRunning()) {
            backAnimation.cancel();
        }
        if (goAnimation == null) {
            goAnimation = AnimationUtils.getInstance().createAnimation(
                    target
                    , animationType
                    , animationDuring
                    , interpolator
                    , null
                    , animationCurrentValue
                    , animationValues);
        } else {
            goAnimation.cancel();
            goAnimation.setFloatValues(animationCurrentValue[0], animationValues[animationValues.length - 1]);
        }
        goAnimation.start();
    }

    public void hide() {
        if (animationCurrentValue[0]
                == animationValues[0]
                && animationCurrentValue[0] != -1
                ) {
            return;
        }
        if (goAnimation != null) {
            goAnimation.cancel();
        }
        if (backAnimation == null) {
            backAnimation = AnimationUtils.getInstance().createAnimation(
                    target
                    , animationType
                    , animationDuring
                    , interpolator
                    , new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (animationCurrentValue[0]
                                    == animationValues[0]) {
                                if (listenerAdapter != null)
                                    listenerAdapter.onAnimationEnd(backAnimation);
                            }
                        }
                    }
                    , animationCurrentValue
                    , animationReversalValues);
        } else {
            backAnimation.cancel();
            backAnimation.setFloatValues(animationCurrentValue[0], animationValues[0]);
        }
        backAnimation.start();
    }
}
