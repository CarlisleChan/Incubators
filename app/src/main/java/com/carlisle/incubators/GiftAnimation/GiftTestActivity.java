package com.carlisle.incubators.GiftAnimation;

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

public class GiftTestActivity extends AppCompatActivity {

    private Button orderBtn;
    private Button normalBtn;

    private RelativeLayout giftContainer;

    private List<String> gifts = new ArrayList<>();
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        setTitle("GiftTest");

        giftContainer = (RelativeLayout) findViewById(R.id.rl_gift_container);

        orderBtn = (Button) findViewById(R.id.btn_order);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftHelper.getInstance(GiftTestActivity.this).stopAnimation();
                mockOrderGift();
            }
        });

        normalBtn = (Button) findViewById(R.id.btn_normal);
        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftHelper.getInstance(GiftTestActivity.this).stopAnimation();
                mockGift();
            }
        });

        GiftHelper.getInstance(this).setContainer(giftContainer);

    }


    private void mockOrderGift() {
        GiftHelper.getInstance(GiftTestActivity.this).importGift(++i + "");
    }

    private void mockGift() {
        GiftHelper.getInstance(GiftTestActivity.this).startAnimation();
    }
}
