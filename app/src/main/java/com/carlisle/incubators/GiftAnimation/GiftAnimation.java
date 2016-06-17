package com.carlisle.incubators.GiftAnimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.carlisle.incubators.R;

/**
 * Created by chengxin on 5/16/16.
 */
public class GiftAnimation {

    private final int FADE_IN_DURATION = 500;
    private final int FADE_OUT_DURATION = 1_000;
    private final int FAST_DROP_DOWN_DURATION = 100;
    private final int SLOW_DROP_DOWN_DURATION = 1_000;
    private final int DELAY = 100;

    private int fadeInPosition = 0;
    private int fadeOutPosition = 0;
    private int fastDropDownPosition = 0;
    private int slowDropDownPosition = 0;

    private AnimatorSet animatorSet;
    private View giftView;

    public GiftAnimation() {

    }

    public void stopAnimation() {
        animatorSet.cancel();
        giftView = null;
    }

    public void startAnimation(final View view) {
        this.giftView = view;
        if (animatorSet == null) {
            animatorSet = new AnimatorSet();
        }

        fadeInPosition = (int) view.getResources().getDimension(R.dimen.fade_in_position);
        fadeOutPosition = (int) view.getResources().getDimension(R.dimen.fade_out_position);
        fastDropDownPosition = (int) view.getResources().getDimension(R.dimen.fast_drop_down_position);
        slowDropDownPosition = (int) view.getResources().getDimension(R.dimen.slow_drop_down_position);

        ObjectAnimator fadeInAnimator;
        fadeInAnimator = ObjectAnimator.ofFloat(giftView, "translationY", fadeInPosition, 0);
        fadeInAnimator.setDuration(FADE_IN_DURATION);
        fadeInAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                giftView.setAlpha(1 - value / 200);
                if (giftView.getAlpha() > 0) {
                    giftView.setVisibility(View.VISIBLE);
                } else {
                    giftView.setVisibility(View.GONE);
                }
            }
        });

        ObjectAnimator fastDropDownAnimator;
        fastDropDownAnimator = ObjectAnimator.ofFloat(giftView, "translationY", 0, fastDropDownPosition);
        fastDropDownAnimator.setDuration(FAST_DROP_DOWN_DURATION);

        ObjectAnimator rotationAnimator;
        rotationAnimator = ObjectAnimator.ofFloat(giftView.findViewById(R.id.iv_gift), "rotationY", 0f, 1080f);
        rotationAnimator.setDuration(FADE_IN_DURATION + SLOW_DROP_DOWN_DURATION);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
//        rotationAnimator.setStartDelay(DELAY);

        ObjectAnimator slowDropDownAnimator;
        slowDropDownAnimator = ObjectAnimator.ofFloat(giftView, "translationY", fastDropDownPosition, slowDropDownPosition);
        slowDropDownAnimator.setDuration(SLOW_DROP_DOWN_DURATION);

        ObjectAnimator fadeOutAnimator;
        fadeOutAnimator = ObjectAnimator.ofFloat(giftView, "translationY", slowDropDownPosition, fadeOutPosition);
        fadeOutAnimator.setDuration(FADE_OUT_DURATION);
        fadeOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                giftView.setAlpha(1 + value / 300);
                if (giftView.getAlpha() > 0) {
                    giftView.setVisibility(View.VISIBLE);
                } else {
                    giftView.setVisibility(View.GONE);
                }
            }
        });

        animatorSet.play(rotationAnimator);
        animatorSet.play(fastDropDownAnimator).after(fadeInAnimator);
        animatorSet.play(slowDropDownAnimator).after(fastDropDownAnimator);
        animatorSet.play(fadeOutAnimator).after(slowDropDownAnimator);
        animatorSet.start();
    }

}
