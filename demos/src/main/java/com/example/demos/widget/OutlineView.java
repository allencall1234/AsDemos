package com.example.demos.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.print.PrintJob;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.demos.R;

/**
 * Created by 524202 on 2017/12/8.
 */

public class OutlineView extends TextView {

    private Paint paint;
    private int startX;
    private int indicatorSize;
    private int outlineColor;
    private int innerColor;
    private boolean isDrawInnerBackground;
    private int strokeWidth;
    private int width;
    private int height;
    private boolean isInit = false;
    private Path path;

    public OutlineView(Context context) {
        this(context, null);
    }

    public OutlineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OutlineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null) {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.OutlineView);
            outlineColor = typedArray.getColor(R.styleable.OutlineView_outline_color, Color.parseColor("#00adb2"));
            innerColor = typedArray.getColor(R.styleable.OutlineView_inner_color, Color.parseColor("#4000adb2"));
            isDrawInnerBackground = typedArray.getBoolean(R.styleable.OutlineView_draw_inner_bg, false);
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.OutlineView_stroke_width, dp2pxInt(1));
            indicatorSize = typedArray.getDimensionPixelSize(R.styleable.OutlineView_indicator_size, dp2pxInt(10));
            typedArray.recycle();
        } else {
            strokeWidth = dp2pxInt(1);
            outlineColor = Color.parseColor("#00adb2");
            innerColor = Color.parseColor("#4000adb2");
            isDrawInnerBackground = false;
            indicatorSize = dp2pxInt(10);
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!isInit) {
            startX = w / 2;
        }
        width = w;
        height = h;
    }

    public void setStartPosition(int startX) {

        startX -= getLeft();

        isInit = true;
        if (startX < indicatorSize / 2) {
            this.startX = indicatorSize / 2;
        } else if (startX > width - indicatorSize / 2) {
            this.startX = width - indicatorSize / 2;
        } else {
            this.startX = startX;
        }
        invalidate();
    }

    private static float dp2px(float dp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    private static int dp2pxInt(float dp) {
        return (int) dp2px(dp);
    }

    public void setStartView(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                setStartPosition(view.getLeft() + view.getMeasuredWidth() / 2);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawOutLine(canvas);
        canvas.save();
        canvas.translate(0, indicatorSize / 2);
        super.onDraw(canvas);
        canvas.restore();
    }

    private void drawOutLine(Canvas canvas) {
        path.reset();
        path.moveTo(startX - indicatorSize / 2, indicatorSize);
        path.lineTo(startX, 0);
        path.lineTo(startX + indicatorSize / 2, indicatorSize);
        path.lineTo(width, indicatorSize);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, indicatorSize);
        path.close();
        if (isDrawInnerBackground) {
            paint.setColor(innerColor);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawPath(path, paint);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(outlineColor);
        canvas.drawPath(path, paint);
    }

}
