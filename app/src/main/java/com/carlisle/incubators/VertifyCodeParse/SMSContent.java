package com.carlisle.incubators.VertifyCodeParse;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chengxin on 2/10/15.
 */
class SMSContent extends ContentObserver {

    private Cursor cursor = null;
    private Activity activity;
    private GetCodeListener getCodeListener;

    public SMSContent(Activity activity, Handler handler, final GetCodeListener getCodeListener) {
        super(handler);
        this.activity = activity;
        this.getCodeListener = getCodeListener;
    }


    public static interface GetCodeListener {
        public void getCode(String code);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        Cursor c = activity.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        if (!c.moveToFirst())
            return;
        String address = c.getString(c.getColumnIndex("address"));
        String body = c.getString(c.getColumnIndex("body"));

        getCodeListener.getCode(getVerfyCode(body, 4));

        if (Build.VERSION.SDK_INT < 14) {
            cursor.close();
        }
    }

    public String getVerfyCode(String body, int length) {

        String number = "[0-9\\\\.]{" + length + "}";

        boolean isVerifycode = body.contains("验")
                && body.contains("证")
                && body.contains("码");

        if (isVerifycode) {
            Pattern p = Pattern.compile(number);
            Matcher m = p.matcher(body);
            if (m.find()) {
                System.out.println(m.group());
                return m.group(0);
            }
        }

        return null;
    }
}
