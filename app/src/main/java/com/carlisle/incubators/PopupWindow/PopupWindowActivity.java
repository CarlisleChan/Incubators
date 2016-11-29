package com.carlisle.incubators.PopupWindow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.carlisle.incubators.R;

/**
 * Created by chengxin on 11/24/16.
 */

public class PopupWindowActivity extends AppCompatActivity {

    private PopupView popupView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        popupView = new PopupView(this);
    }

    public void onButtonClick(View view) {
        //popupView.showAsDropDown(view);
        popupView.showAsDropUp(view);
    }
}
