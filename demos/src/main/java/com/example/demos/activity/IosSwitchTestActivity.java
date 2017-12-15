package com.example.demos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.widget.OutlineView;
import com.example.demos.widget.IosSwitch;

/**
 * Created by 524202 on 2017/12/7.
 */

public class IosSwitchTestActivity extends AppCompatActivity implements View.OnClickListener {

    IosSwitch iosSwitch = null;
    TextView textView = null;
    Button gotoOtherActivity = null;
    String switchState = "关闭";
    OutlineView outlineView = null;
    SeekBar seekBar = null;

    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ios_switch_activity);
        iosSwitch = (IosSwitch) findViewById(R.id.ios_switch);
        textView = (TextView) findViewById(R.id.ios_switch_value);

        iosSwitch.setOnToggleListener(new IosSwitch.OnToggleListener() {
            @Override
            public void onSwitchChangeListener(boolean isChecked) {
                switchState = isChecked ? "打开" : "关闭";
                textView.setText("状态 ：" + switchState);
            }
        });

        gotoOtherActivity = (Button) findViewById(R.id.btn_goto);
        gotoOtherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IosSwitchTestActivity.this, EmptyActivity.class);
                intent.putExtra("switch_state", switchState);
                startActivity(intent);
            }
        });

        outlineView = (OutlineView) findViewById(R.id.indicatior_textview);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                outlineView.setStartPosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();

            }
        });

        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        iosSwitch.cancelAnimate();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        outlineView.setStartView(v);
    }
}
