package com.carlisle.incubators.SecretTextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.carlisle.incubators.R;

/**
 * Created by matt on 5/27/14.
 */
public class SecretTextViewActivity extends AppCompatActivity {

    SecretTextView secretTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_text_view);

        secretTextView = (SecretTextView)findViewById(R.id.textview);
        secretTextView.setmDuration(3000);
        secretTextView.toggle();
        secretTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secretTextView.toggle();
            }
        });
        
    }
}
