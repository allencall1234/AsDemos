package com.example.showpic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 524202 on 2017/12/28.
 */

public class RoundRectTextView extends TextView {

    private int radius = 20;
    private Paint mPaint = null;
    private int width;
    private int height;
    private RectF mRectF = new RectF();

    public RoundRectTextView(Context context) {
        super(context);
        init();
    }

    public RoundRectTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundRectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mRectF.set(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setColor(Color.parseColor("#52bfda"));
        canvas.drawRoundRect(mRectF, radius, radius, mPaint);
        super.onDraw(canvas);
    }
}
