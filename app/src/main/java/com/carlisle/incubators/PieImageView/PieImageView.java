package com.carlisle.incubators.PieImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.carlisle.incubators.R;

/**
 * Created by chengxin on 5/16/16.
 */
public class PieImageView extends ImageView implements View.OnClickListener {
    private final int ONE_HUNDRED_PERCENT = 100;
    private final int MILLI_SECOND = 1_000;
    private final int DEFAULT_TIME = 1_000;

    private int curPercent = ONE_HUNDRED_PERCENT;
    private float rate;
    private boolean isPlaying = false;

    private int bgColorId = Color.parseColor("#A7A7A7");
    private int forColorId = Color.parseColor("#cdff6d00");

    private Drawable drawable;

    public PieImageView(Context context) {
        super(context);
        init();
    }

    public PieImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint sectorPaint = new Paint();
        sectorPaint.setColor(bgColorId);
        sectorPaint.setAntiAlias(true);

        int length = (int) getResources().getDimension(R.dimen.pie_size);

        RectF rect = new RectF(0, 0, length, length);
        canvas.drawArc(rect, 0, 360, true, sectorPaint);

        sectorPaint.setColor(forColorId);
        rect = new RectF(0, 0, length, length);
        canvas.drawArc(rect, 270, -getEndAngle(), true, sectorPaint);

        int left = getPaddingLeft();
        int top = getPaddingLeft();
        int right = getWidth() - getPaddingRight();
        int bottom = getWidth() - getPaddingBottom();

        drawable = getResources().getDrawable(android.R.drawable.ic_menu_camera);
        drawable.setBounds(left, top, right, bottom);
        drawable.draw(canvas);
    }

    private int getEndAngle() {
        return (int) (curPercent * 3.6);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void startCountDown() {
        startCountDown(DEFAULT_TIME);
    }

    public void startCountDown(long milliTime) {
        setMilliTime(milliTime);
    }

    private void setMilliTime(long milliTime) {
        if (isPlaying) {
            return;
        }

        rate = (milliTime / (float) MILLI_SECOND) / ONE_HUNDRED_PERCENT;

        new Thread(new Runnable() {
            @Override
            public void run() {
                isPlaying = true;
                int sleepTime = 1000;
                for (int i = 0; i <= ONE_HUNDRED_PERCENT; i++) {
                    try {
                        Thread.sleep((long) (sleepTime * rate));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    curPercent = i;
                    PieImageView.this.postInvalidate();
                }
                isPlaying = false;
            }

        }).start();
    }

    @Override
    public void onClick(View v) {
        startCountDown();
    }

}