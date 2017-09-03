package com.example.demos.activity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.widget.StableEditText;

/**
 * Created by zlt on 2017/9/2.
 */

public class StableEditTextActivity extends FragmentActivity{
    StableEditText stableEdit;
    EditText inputEdit;
    Button addBtn,getBtn;
    TextView tvShowText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stable_edit_layout);
        stableEdit = (StableEditText) findViewById(R.id.stable_edit);
        inputEdit = (EditText) findViewById(R.id.input_edit);
        addBtn = (Button) findViewById(R.id.add_text_btn);
        getBtn = (Button) findViewById(R.id.get_text_btn);
        tvShowText = (TextView) findViewById(R.id.show_text_tv);

        stableEdit.setStableText("卖家回复:");

        stableEdit.setText("卖家回复:");
        stableEdit.setText("卖家回复11:");
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addVal = inputEdit.getText().toString();
                if (addVal != null){
                    stableEdit.setText(addVal);
                    tvShowText.setText(stableEdit.getFinalText());
                }
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShowText.setText(stableEdit.getFinalText());
            }
        });
    }
}
