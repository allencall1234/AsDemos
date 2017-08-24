package com.example.all_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by 524202 on 2017/8/14.
 */

public class DividerRelativeLayout extends RelativeLayout {

    private Paint mPaint = null;

    public DividerRelativeLayout(Context context) {
        super(context);
        init();
    }

    public DividerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DividerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#888888"));
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }
}
