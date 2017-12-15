package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by 524202 on 2017/12/7.
 */

public class EmptyActivity extends AppCompatActivity {

    String switchState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        setContentView(textView);

        switchState = getIntent().getStringExtra("switch_state");
        if (switchState != null) {
            textView.setText("开关状态:" + switchState);
        }
    }
}
