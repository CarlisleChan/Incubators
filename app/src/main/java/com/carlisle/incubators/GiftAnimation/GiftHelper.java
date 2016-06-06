package com.carlisle.incubators.GiftAnimation;

import android.os.CountDownTimer;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by chengxin on 5/16/16.
 */
public class GiftHelper {
    private final String GIFT_CONFIG = "gift_config";

    private static GiftHelper instance;
    private GiftAnimation giftAnimation;

    private Queue<String> queue = new LinkedList<String>();
    private CountDownTimer countDownTimer;

    private boolean isPlaying = false;
    private boolean canPlay = true;

    public static GiftHelper getInstance() {
        if (instance == null) {
            instance = new GiftHelper();
        }
        return instance;
    }

    public void importGift(String gift, View view) {
        queue.offer(gift);
        if (canPlay && !isPlaying) {
            startAnimation(view);
        }
    }

    public void importGifts(List<String> gifts, View view) {
        queue.addAll(gifts);
        if (canPlay && !isPlaying) {
            startAnimation(view);
        }
    }

    public void startAnimation(final View view) {
        canPlay = true;
        isPlaying = true;

        if (!queue.isEmpty()) {
            queue.poll();
            countDownTimer = new CountDownTimer(25_00, 1_000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    startAnimation(view);
                }
            }.start();
        } else {
            isPlaying = false;
        }

        if (giftAnimation == null) {
            giftAnimation = new GiftAnimation();
        }

        giftAnimation.startAnimation(view);
    }

    public void requestConfig() {
        // TODO: 5/16/16  pull and save
    }

    public void stopAnimation() {
        canPlay = false;
        isPlaying = false;

        if (giftAnimation != null) {
            giftAnimation.stopAnimation();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
