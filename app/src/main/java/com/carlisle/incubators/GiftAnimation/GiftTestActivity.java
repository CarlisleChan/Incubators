package com.carlisle.incubators.GiftAnimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.carlisle.incubators.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengxin on 6/6/16.
 */

public class GiftTestActivity extends Activity {

    private Button addBtn;

    private RelativeLayout giftLayout;
    private ImageView giftImageView;

    private List<String> gifts = new ArrayList<>();
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        giftLayout = (RelativeLayout) findViewById(R.id.rl_gift);
        giftImageView = (ImageView) findViewById(R.id.iv_gift);

        addBtn = (Button) findViewById(R.id.btn_replay);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftHelper.getInstance().importGift(++i + "", giftLayout);

            }
        });

        for (; i < 5; i++) {
            gifts.add(i + "");
        }

        GiftHelper.getInstance().importGifts(gifts, giftLayout);
    }
}
