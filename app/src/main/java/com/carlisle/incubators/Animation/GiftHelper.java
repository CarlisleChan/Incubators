package com.carlisle.incubators.Animation;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;

import com.carlisle.incubators.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by chengxin on 5/16/16.
 */
public class GiftHelper {
    private final String TAG = "GiftHelper";
    private final String GIFT_CONFIG = "gift_config";

    private Context context;
    private RelativeLayout container;

    private static GiftHelper instance;

    // order animation
    private GiftAnimation giftAnimation;
    private Queue<String> queue = new LinkedList<String>();
    private CountDownTimer countDownTimer;
    private boolean isPlaying = false;
    private boolean canPlay = true;
    private View targetView;

    public static GiftHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GiftHelper();
        }
        instance.context = context;
        return instance;
    }

    public void importGift(String gift) {
        queue.offer(gift);
        if (canPlay && !isPlaying) {
            startOrderlyAnimation();
        }
    }

    public void importGifts(List<String> gifts) {
        queue.addAll(gifts);
        if (canPlay && !isPlaying) {
            startOrderlyAnimation();
        }
    }

    private void startOrderlyAnimation() {
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
                    startOrderlyAnimation();
                }
            }.start();
        } else {
            isPlaying = false;
        }

        if (giftAnimation == null) {
            giftAnimation = new GiftAnimation();
        }

        if (targetView == null) {
            targetView = View.inflate(context, R.layout.widget_gift, null);
            container.addView(targetView);
        }
        giftAnimation.startAnimation(targetView);
    }

    public void startAnimation() {
        if (container == null) {
            return;
        }

        if (container.getVisibility() != View.VISIBLE) {
            container.removeAllViews();
        }

        View view = View.inflate(context, R.layout.widget_gift, null);
        container.addView(view);

        GiftAnimation giftAnimation = new GiftAnimation();

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

    public void setContainer(RelativeLayout container) {
        this.container = container;
    }
}
