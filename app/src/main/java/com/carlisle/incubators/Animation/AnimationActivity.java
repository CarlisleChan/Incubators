package com.carlisle.incubators.Animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.carlisle.incubators.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengxin on 6/6/16.
 */

public class AnimationActivity extends AppCompatActivity {

    private Button orderBtn;
    private Button normalBtn;

    private RelativeLayout giftContainer;

    private List<String> gifts = new ArrayList<>();
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation     );
        setTitle("GiftTest");

        giftContainer = (RelativeLayout) findViewById(R.id.rl_gift_container);

        orderBtn = (Button) findViewById(R.id.btn_order);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftHelper.getInstance(AnimationActivity.this).stopAnimation();
                mockOrderGift();
            }
        });

        normalBtn = (Button) findViewById(R.id.btn_normal);
        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftHelper.getInstance(AnimationActivity.this).stopAnimation();
                mockGift();
            }
        });

        GiftHelper.getInstance(this).setContainer(giftContainer);

    }


    private void mockOrderGift() {
        GiftHelper.getInstance(AnimationActivity.this).importGift(++i + "");
    }

    private void mockGift() {
        GiftHelper.getInstance(AnimationActivity.this).startAnimation();
    }
}
