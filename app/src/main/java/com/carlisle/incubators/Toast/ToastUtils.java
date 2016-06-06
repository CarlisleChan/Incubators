package com.carlisle.incubators.Toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chengxin on 6/6/16.
 */

public class ToastUtils {
    public static Toast toast = null;

    public static void showToast(Context context, String content) {
        if (null == toast) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void cancel() {
        if (null != toast) {
            toast.cancel();
        }
    }
}
