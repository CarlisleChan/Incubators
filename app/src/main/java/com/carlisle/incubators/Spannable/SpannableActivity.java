package com.carlisle.incubators.Spannable;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.carlisle.incubators.R;

/**
 * Created by chengxin on 6/1/16.
 */

public class SpannableActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);

        textView = (TextView) findViewById(R.id.text_view);

        String username = " Jake ";
        String age = " 118 ";
        String like = " football  ";
        String content = username + age + like;

        Spannable spannable = new SpannableString(content);

        int usernameStartPosition = content.indexOf(username);
        int usernameEndPosition = usernameStartPosition + username.length();
        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), usernameStartPosition, usernameEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int ageStartPosition = content.indexOf(age);
        int ageEndPosition = ageStartPosition + age.length();
        RoundedBackgroundSpan span = new RoundedBackgroundSpan();
        span.setBackgroundColor(Color.BLUE);
        span.setCornerRadius(20);
        span.setMarginTop(10);
        span.setMarginBottom(10);
        spannable.setSpan(span, ageStartPosition, ageEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
        spannable.setSpan(new RelativeSizeSpan(0.6f), ageStartPosition, ageEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.GRAY), ageStartPosition, ageEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int likeStartPosition = content.indexOf(like);
        int likeEndPosition = likeStartPosition + like.length();
        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), likeStartPosition, likeEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannable);

    }
}
