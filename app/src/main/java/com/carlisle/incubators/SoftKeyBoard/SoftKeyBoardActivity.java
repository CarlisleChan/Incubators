package com.carlisle.incubators.SoftKeyBoard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.carlisle.incubators.R;

/**
 * Created by chengxin on 6/15/16.
 */

public class SoftKeyBoardActivity extends AppCompatActivity implements KeyboardDetectorRelativeLayout.KeyboardChangedListener {
    private final String TAG = "SoftKeyBoardActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_key_board);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ((KeyboardDetectorRelativeLayout) findViewById(R.id.rl_root_layout)).addKeyboardStateChangedListener(this);
    }

    @Override
    public void onKeyboardShown() {
        Log.d(TAG, "onShown");
    }

    @Override
    public void onKeyboardHidden() {
        Log.d(TAG, "onHidden");
    }
}
