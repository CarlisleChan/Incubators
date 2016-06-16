package com.carlisle.incubators.Spannable;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class SpannableActivity extends AppCompatActivity {

    private TextView textView;
    private TextView leftFakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);
        setTitle("Spannable");

        textView = (TextView) findViewById(R.id.tv_content);
        leftFakeView = (TextView) findViewById(R.id.tv_avatar_holder);

        String username = " Jake ";
        String age = " 118 ";
        String like = " football  +  fdsa";
        String content = username + age + like;

        Spannable spannable = new SpannableString(content);

        int usernameStartPosition = content.indexOf(username);
        int usernameEndPosition = usernameStartPosition + username.length();

        RoundedBackgroundSpan span = new RoundedBackgroundSpan();
        span.setBackgroundColor(Color.BLUE);
        span.setCornerRadius(50);
        span.setLeftTopRadius(0);
        span.setLeftBottomRadius(0);
        spannable.setSpan(span, usernameStartPosition, usernameEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int ageStartPosition = content.indexOf(age);
        int ageEndPosition = ageStartPosition + age.length();
        span = new RoundedBackgroundSpan();
        span.setBackgroundColor(Color.BLUE);
        span.setCornerRadius(20);
        span.setMarginTop(10);
        span.setMarginBottom(10);
        spannable.setSpan(span, ageStartPosition, ageEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(new RelativeSizeSpan(0.6f), ageStartPosition, ageEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.GRAY), ageStartPosition, ageEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int likeStartPosition = content.indexOf(like);
        int likeEndPosition = likeStartPosition + like.length();
        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), likeStartPosition, likeEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannable);

        String content1 = "     ";
        spannable = new SpannableString(content1);
        RoundedBackgroundSpan span1 = new RoundedBackgroundSpan();
        span1.setBackgroundColor(Color.BLUE);
        span1.setCornerRadius(50);
        span1.setRightTopRadius(0);
        span1.setRightBottomRadius(0);
        span1.setLeftBottomRadius(10);
        span1.setLeftTopRadius(10);
        span1.setMarginBottom(2);

        spannable.setSpan(span1, content1.indexOf(" ") + 1, content1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        leftFakeView.setText(spannable);
    }
}
