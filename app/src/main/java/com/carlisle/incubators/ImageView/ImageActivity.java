package com.carlisle.incubators.ImageView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.carlisle.incubators.R;

/**
 * Created by chengxin on 6/6/16.
 */

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        setTitle("PieImage");
    }
}
