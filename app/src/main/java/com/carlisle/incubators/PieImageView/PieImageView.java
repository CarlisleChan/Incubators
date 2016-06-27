package com.carlisle.incubators.PieImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.carlisle.incubators.R;

/**
 * Created by chengxin on 5/16/16.
 */
public class PieImageView extends ImageView implements View.OnClickListener {
    private final String TAG = "PieImageView";

    private final int ONE_HUNDRED_PERCENT = 100;
    private final int MILLI_SECOND = 1_000;
    private final int DEFAULT_TIME = 1_000;
    private final int DEFAULT_BORDER_WIDTH = 0;

    private int curPercent = ONE_HUNDRED_PERCENT;
    private float rate;
    private boolean isPlaying = false;

    private int bgColor = Color.TRANSPARENT;
    private int forColor = Color.TRANSPARENT;
    private int borderColor = Color.TRANSPARENT;

    private int borderWidth = DEFAULT_BORDER_WIDTH;
    private int pieSize;
    private long countTime = DEFAULT_TIME;

    private Paint bgSectorPaint = new Paint();
    private Paint forSectorPaint = new Paint();
    private Paint borderSectorPaint = new Paint();

    private RectF rect;

    public PieImageView(Context context) {
        super(context);
    }

    public PieImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PieImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.setOnClickListener(this);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PieImageView);
        bgColor = typedArray.getColor(R.styleable.PieImageView_backgroundColor, Color.TRANSPARENT);
        forColor = typedArray.getColor(R.styleable.PieImageView_forgroundColor, Color.TRANSPARENT);
        borderColor = typedArray.getColor(R.styleable.PieImageView_pieBorderColor, Color.TRANSPARENT);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.PieImageView_pieBorderWidth, DEFAULT_BORDER_WIDTH);
        countTime = (long) (typedArray.getFloat(R.styleable.PieImageView_countTime, 1.0f) * 1000);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getWidth() > getHeight()) {
            pieSize = getHeight() - borderWidth;
        } else {
            pieSize = getWidth() - borderWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw border
        int left = 0;
        int top = 0;
        int right = pieSize + borderWidth;
        int bottom = pieSize + borderWidth;

        borderSectorPaint.setColor(borderColor);
        borderSectorPaint.setAntiAlias(true);
        rect = new RectF(left, top, right, bottom);
        canvas.drawArc(rect, 0, 360, true, borderSectorPaint);

        // draw background
        bgSectorPaint.setColor(bgColor);
        bgSectorPaint.setAntiAlias(true);
        rect = new RectF(borderWidth, borderWidth, pieSize, pieSize);
        canvas.drawArc(rect, 0, 360, true, bgSectorPaint);

        // draw forground
        forSectorPaint.setColor(forColor);
        forSectorPaint.setAntiAlias(true);
        canvas.drawArc(rect, 270, -getEndAngle(), true, forSectorPaint);

        super.onDraw(canvas);
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setForColor(int forColor) {
        this.forColor = forColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    private int getEndAngle() {
        return (int) (curPercent * 3.6);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void startCountDown() {
        startCountDown(countTime);
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