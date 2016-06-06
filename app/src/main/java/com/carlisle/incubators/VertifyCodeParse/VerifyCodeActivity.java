package com.carlisle.incubators.VertifyCodeParse;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.carlisle.incubators.R;

public class VerifyCodeActivity extends AppCompatActivity {

    private SMSContent smsContent;
    private TextView codeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_code);
        codeView = (TextView) findViewById(R.id.verify_code);
        smsContent = new SMSContent(this, new Handler(), new SMSContent.GetCodeListener() {
            @Override
            public void getCode(String verifyCode) {
                codeView.setText(getString(R.string.verify_code, verifyCode));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(smsContent);
    }


}
