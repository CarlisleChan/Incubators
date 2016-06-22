package com.carlisle.incubators.SoftKeyBoard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by chengxin on 6/16/16.
 */

public class KeyboardDetectorRelativeLayout extends RelativeLayout {

    public interface KeyboardChangedListener {
        void onKeyboardShown();

        void onKeyboardHidden();
    }

    private ArrayList<KeyboardChangedListener> keyboardListener = new ArrayList<KeyboardChangedListener>();

    public KeyboardDetectorRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public KeyboardDetectorRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardDetectorRelativeLayout(Context context) {
        super(context);
    }

    public void addKeyboardStateChangedListener(KeyboardChangedListener listener) {
        keyboardListener.add(listener);
    }

    public void removeKeyboardStateChangedListener(KeyboardChangedListener listener) {
        keyboardListener.remove(listener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int proposedheight = MeasureSpec.getSize(heightMeasureSpec);
        final int actualHeight = getHeight();

        if (actualHeight > proposedheight) {
            notifyKeyboardShown();
        } else if (actualHeight < proposedheight) {
            notifyKeyboardHidden();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void notifyKeyboardHidden() {
        for (KeyboardChangedListener listener : keyboardListener) {
            listener.onKeyboardHidden();
        }
    }

    private void notifyKeyboardShown() {
        for (KeyboardChangedListener listener : keyboardListener) {
            listener.onKeyboardShown();
        }
    }

}
