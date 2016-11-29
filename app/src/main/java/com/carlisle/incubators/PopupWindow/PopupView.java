package com.carlisle.incubators.PopupWindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.carlisle.incubators.R;

/**
 * Created by chengxin on 11/24/16.
 */

public class PopupView extends PopupWindow {

    private Context context;
    private View view;

    public PopupView(Context context) {
        this.context = context;
        view = View.inflate(context, R.layout.layout_popup_window, null);

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
    }

    public void showAsDropUp(View targetView) {
        int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        int offset = 50;
        showAtLocation(targetView, Gravity.NO_GRAVITY, location[0], location[1] - offset);
    }
}
