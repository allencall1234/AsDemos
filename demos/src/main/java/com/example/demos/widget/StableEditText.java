package com.example.demos.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zlt on 2017/9/2.
 * 左侧固定内容的EditText
 * 用于回复/评论
 */

public class StableEditText extends android.support.v7.widget.AppCompatEditText {

    public final static int DEFAULT_STABLE_TEXT_COLOR = Color.parseColor("#333333");

    private CharSequence stableText = "";
    private ForegroundColorSpan colorSpan = null;
    private int stableTextColor;
    /**
     * 区分设置StableText标志，每次设置StableText前置位false,设置成功后置为true
     * 因为无论是StableText还是其他的Text,都会调用setText()方法，
     * 所以在setText()方法中添加判断：initStableText为false表示设置StableText;false表示设置其他Text
     */
    private boolean initStableText = true;

    private boolean isFirstIn = true;

    private int start = 0;
    private int end = 0;

    public StableEditText(Context context) {
        super(context);
        init();
    }

    public StableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        stableTextColor = DEFAULT_STABLE_TEXT_COLOR;
        addTextChangedListener(new TextWatcher() {
            String textBefore;

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().startsWith(stableText.toString())) {
                    textBefore = s.subSequence(stableText.length(), s.length()).toString();
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < stableText.length()) {
                    //回删的时候避免把StableText内容删除
                    initStableText = false;
                    setText(stableText);
                    //避免光标移动到StableText内容中间
                    setSelection(stableText.length());
                } else {
                    //中间开始回删的时候避免把StableText内容删除
                    String text = s.toString();
                    String tempStableText = text.substring(0, stableText.length());
                    if (!tempStableText.equals(stableText)) {
                        //如果StableText被删除,
                        setText(textBefore);
                    }
                }

//                if (isEmoji(s.toString())) {
//                    if (emojiCheckListener != null) {
//                        emojiCheckListener.onCheckEmoji();
//                    }
//                    setText(textBefore);
//                }
            }
        });
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

//        if (text != null && isEmoji(text.toString())){
//            if (emojiCheckListener != null){
//                emojiCheckListener.onCheckEmoji();
//            }
//            return;
//        }

        if (isFirstIn) {
            super.setText(text, type);
            return;
        }

        String newText;
        if (text == null) {
            text = "";
        }

        if (stableText == null) {
            stableText = "";
        }

        if (initStableText) {
            newText = stableText.toString() + text;
        } else {
            newText = text.toString();
            initStableText = true;
        }

        if (end > start) {
            colorSpan = new ForegroundColorSpan(stableTextColor);
            SpannableString spannableString = new SpannableString(newText);
            spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            super.setText(spannableString, type);
        } else {
            super.setText(newText, type);
        }

        setSelection(newText.length());
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        int length = stableText.length();
        if (selStart < length) {
            setSelection(length);
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && isFirstIn) {
            setText("");
//            setTextColor(Color.parseColor("#4a4a4a"));
            isFirstIn = false;
            if (firstInListener != null) {
                firstInListener.firstIn();
            }
        }
    }

    /**
     * 设置左侧固定字体
     *
     * @param s 左侧固定字体
     */
    public void setStableText(CharSequence s) {
        stableText = s;
        initStableText = false;
        setText(stableText);
    }

    public void setSpannerStableText(CharSequence s, int start, int end, int color) {
        stableText = s;
        initStableText = false;
        this.start = start;
        this.end = end;
        stableTextColor = color;
        setText(stableText);
    }

    /**
     * 返回除固定字体外的内容
     *
     * @return
     */
    public String getFinalText() {
        if (isFirstIn) {
            return null;
        }
        String reslut = getText().toString();
        if (stableText == null) {
            return reslut;
        }
        return reslut.substring(stableText.length());
    }

    public OnFirstInListener firstInListener;

    public void setOnFirstInListener(OnFirstInListener listener) {
        firstInListener = listener;
    }

    public interface OnFirstInListener {
        void firstIn();
    }

    public OnEmojiCheckListener emojiCheckListener;

    public void setOnEmojiCheckListener(OnEmojiCheckListener listener) {
        emojiCheckListener = listener;
    }

    public interface OnEmojiCheckListener {
        void onCheckEmoji();
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    public boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }
}