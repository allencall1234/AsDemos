package com.example.demos.activity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.widget.StableEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zlt on 2017/9/2.
 */

public class StableEditTextActivity extends FragmentActivity {
    StableEditText stableEdit;
    EditText inputEdit;
    Button addBtn, getBtn;
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

        SpannableString desc = new SpannableString("买家回复（选填）：对本次交易的说明");
        desc.setSpan(new ForegroundColorSpan(Color.parseColor("#4a4a4a")), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        desc.setSpan(new ForegroundColorSpan(Color.parseColor("#00adb2")), 4, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        desc.setSpan(new ForegroundColorSpan(Color.parseColor("#4a4a4a")), 8, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        desc.setSpan(new ForegroundColorSpan(Color.parseColor("#cccccc")), 9, desc.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        stableEdit.setText(desc);
        stableEdit.setOnFirstInListener(new StableEditText.OnFirstInListener() {
            @Override
            public void firstIn() {
                stableEdit.setSpannerStableText("买家回复（选填）：", 4, 8, Color.parseColor("#00abd2"));
            }
        });
        stableEdit.setOnEmojiCheckListener(new StableEditText.OnEmojiCheckListener() {
            @Override
            public void onCheckEmoji() {
                showToast("抱歉，目前不支持表情符号");
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addVal = inputEdit.getText().toString();
                if (addVal != null) {
                    stableEdit.setText(addVal);
                    tvShowText.setText(stableEdit.getFinalText());
                }
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = stableEdit.getFinalText();
                text = string2Unicode(text);
                Log.d("zlt", "text = " + text);
//                if (isEmoji(text)){
//                    showToast("抱歉，目前不支持表情符号");
//                    return;
//                }
                Toast.makeText(StableEditTextActivity.this, text, Toast.LENGTH_SHORT).show();
                tvShowText.setText(unicode2String(text));
            }
        });
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    public boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    private Toast mToast;

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(StableEditTextActivity.this, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
