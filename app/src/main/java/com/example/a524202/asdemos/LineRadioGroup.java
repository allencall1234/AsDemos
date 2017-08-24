package com.example.a524202.asdemos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioGroup;

/**
 * Created by 524202 on 2017/8/9.
 */

public class LineRadioGroup extends RadioGroup {

    private Paint mPaint;

    public LineRadioGroup(Context context) {
        super(context);
        initPaint(context);
    }

    public LineRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    private void initPaint(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("zlt", "width = " + getMeasuredWidth());
        super.onDraw(canvas);
        canvas.drawLine(0, 0, getMeasuredWidth(), 0, mPaint);
    }
}
