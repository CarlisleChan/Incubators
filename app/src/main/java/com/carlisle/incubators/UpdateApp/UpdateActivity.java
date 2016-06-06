package com.carlisle.incubators.UpdateApp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.carlisle.incubators.R;

/**
 * Created by chengxin on 6/6/16.
 */

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        findViewById(R.id.tv_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateManager.getInstance(UpdateActivity.this).checkUpdateWithParams(29, "8.0.0");
            }
        });
    }
}
