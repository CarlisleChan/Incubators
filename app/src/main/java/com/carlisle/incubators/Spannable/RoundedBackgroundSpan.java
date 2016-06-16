package com.carlisle.incubators.Spannable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import android.util.Log;

/**
 * Created by chengxin on 6/1/16.
 */

public class RoundedBackgroundSpan extends ReplacementSpan {
    private String TAG = "RoundedBackgroundSpan";

    private static int DEFAULT_CORNER_RADIUS = 10;

    private int backgroundColor = Color.TRANSPARENT;
    private int textColor = Color.WHITE;

    private int cornerRadius = DEFAULT_CORNER_RADIUS;
    private int leftTopRadius = DEFAULT_CORNER_RADIUS;
    private int rightTopRadius = DEFAULT_CORNER_RADIUS;
    private int leftBottomRadius = DEFAULT_CORNER_RADIUS;
    private int rightBottomRadius = DEFAULT_CORNER_RADIUS;

    private int marginTop = 0;
    private int marginBottom = 0;
    private int marginRight = 0;
    private int marginLeft = 0;

    private RectF roundRect;
    private RectF leftTopRect;
    private RectF leftBottomRect;
    private RectF rightTopRect;
    private RectF rightBottomRect;

    public RoundedBackgroundSpan() {
        super();
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Log.d(TAG, "draw");
        float leftPositiion = x + marginLeft;
        float topPositiion = top + marginTop;
        float rightPositiion = x + measureText(paint, text, start, end) - marginRight;
        float bottomPositiion = bottom - marginBottom;

        if (roundRect == null) {
            roundRect = new RectF(leftPositiion, topPositiion, rightPositiion, bottomPositiion);
        }
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(roundRect, cornerRadius, cornerRadius, paint);

        if (leftTopRadius != DEFAULT_CORNER_RADIUS) {
            if (leftTopRect == null) {
                leftTopRect = new RectF(leftPositiion, topPositiion, rightPositiion / 2, bottomPositiion / 2);
            }
            canvas.drawRect(leftTopRect, paint);
        }

        if (leftBottomRadius != DEFAULT_CORNER_RADIUS) {
            if (leftBottomRect == null) {
                leftBottomRect = new RectF(leftPositiion, topPositiion, rightPositiion / 2, bottomPositiion);
            }
            canvas.drawRect(leftBottomRect, paint);
        }

        if (rightTopRadius != DEFAULT_CORNER_RADIUS) {
            if (rightTopRect == null) {
                rightTopRect = new RectF(leftPositiion / 2, topPositiion, rightPositiion, bottomPositiion / 2);
            }
            canvas.drawRect(rightTopRect, paint);
        }

        if (rightBottomRadius != DEFAULT_CORNER_RADIUS) {
            if (rightBottomRect == null) {
                rightBottomRect = new RectF(leftPositiion / 2, topPositiion / 2, rightPositiion, bottomPositiion);
            }
            canvas.drawRect(rightBottomRect, paint);
        }

        float textX = x - marginLeft;
        float textY = y - marginTop;
        paint.setColor(textColor);
        canvas.drawText(text, start, end, textX, textY, paint);

        Log.d(TAG, "\nx:" + x + "\ny:" + y + "\nstart :" + start + "\nend:" + end + "\ntop:" + top + "\nbottom:" + bottom);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end));
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setLeftBottomRadius(int leftBottomRadius) {
        this.leftBottomRadius = leftBottomRadius;
    }

    public void setLeftTopRadius(int leftTopRadius) {
        this.leftTopRadius = leftTopRadius;
    }

    public void setRightBottomRadius(int rightBottomRadius) {
        this.rightBottomRadius = rightBottomRadius;
    }

    public void setRightTopRadius(int rightTopRadius) {
        this.rightTopRadius = rightTopRadius;
    }

    public void setMargin(int margin) {
        this.marginBottom = margin;
        this.marginTop = margin;
        this.marginRight = margin;
        this.marginLeft = margin;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

}
