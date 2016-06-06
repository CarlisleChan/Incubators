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

    private int cornerRadius = DEFAULT_CORNER_RADIUS;
    private int backgroundColor = Color.TRANSPARENT;
    private int textColor = Color.WHITE;
    private int marginTop = 0;
    private int marginBottom = 0;
    private int marginRight = 0;
    private int marginLeft = 0;

    public RoundedBackgroundSpan() {
        super();
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float leftPositiion = x + marginLeft;
        float topPositiion = top + marginTop;
        float rightPositiion = x + measureText(paint, text, start, end) - marginRight;
        float bottomPositiion = bottom - marginBottom;

        RectF rect = new RectF(leftPositiion, topPositiion, rightPositiion, bottomPositiion);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

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
