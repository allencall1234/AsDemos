package com.example.demos.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created by 524202 on 2017/12/7.
 */
public class CustomSwitch extends View implements OnTouchListener {

    private Bitmap bg_on, bg_off, slider;
    /**
     * 按下时的x坐标和当前的x坐标
     */
    private float downX, nowX;
    /**
     * 判断当前滑动状态
     */
    private boolean isSlip;

    private boolean nowStatus;

    private OnChangeListener listener;

    private final static int DEFAULT_WIDTH = dp2pxInt(52);
    private final static int DEFAULT_HEIGHT = dp2pxInt(30);
    private final static int SLIDER_SIZE = dp2pxInt(26);
    private final static int BORDER_WIDTH = dp2pxInt(2);

    private int bgColorOn = Color.parseColor("#00adb2");
    private int bgColorOff = Color.parseColor("#a7a7a7");
    private int sliderColor = Color.WHITE;
    private int sliderStroke = Color.parseColor("#dddddd");


    public CustomSwitch(Context context) {
        this(context, null);
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub

        bg_on = createBackground(bgColorOn);
        bg_off = createBackground(bgColorOff);
        slider = createButton();

        setOnTouchListener(this);
    }


    private Bitmap createBackground(int color) {
        Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT,
                    DEFAULT_HEIGHT, DEFAULT_HEIGHT, paint);
        } else {
            RectF rect = new RectF(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            canvas.drawRoundRect(rect,
                    DEFAULT_HEIGHT, DEFAULT_HEIGHT, paint);
        }
        return bitmap;
    }

    private Bitmap createButton() {
        Bitmap bitmap = Bitmap.createBitmap(SLIDER_SIZE, SLIDER_SIZE, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(sliderColor);
        canvas.drawCircle(SLIDER_SIZE / 2, SLIDER_SIZE / 2, SLIDER_SIZE / 2, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(sliderStroke);
        canvas.drawCircle(SLIDER_SIZE / 2, SLIDER_SIZE / 2, SLIDER_SIZE / 2, paint);
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub

        int resultWidth = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            resultWidth = specSize;
        } else {
            resultWidth = DEFAULT_WIDTH + getPaddingLeft() + getPaddingRight();

            if (specMode == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth, specSize);
            }
        }

        int resultHeight = 0;
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            resultHeight = specSize;
        } else {
            resultHeight = DEFAULT_HEIGHT + getPaddingTop() + getPaddingBottom();

            if (specMode == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, specSize);
            }
        }

        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (downX < (DEFAULT_WIDTH / 2 - SLIDER_SIZE / 2)) {
            canvas.drawBitmap(bg_off, getPaddingLeft(), getPaddingTop(), null);
        } else {
            canvas.drawBitmap(bg_on, getPaddingLeft(), getPaddingTop(), null);
        }

        if (isSlip) {
            if (downX >= (DEFAULT_WIDTH / 2)) {
                downX = DEFAULT_WIDTH - SLIDER_SIZE / 2;
            } else {
                downX = nowX - SLIDER_SIZE / 2;
            }
        } else {
            if (nowStatus) {
                downX = DEFAULT_WIDTH - SLIDER_SIZE / 2;
            } else {
                downX = 0;
            }
        }

        if (downX < BORDER_WIDTH) {
            downX = BORDER_WIDTH;
        } else if (downX > DEFAULT_WIDTH - SLIDER_SIZE - BORDER_WIDTH) {
            downX = DEFAULT_WIDTH - SLIDER_SIZE - BORDER_WIDTH;
        }

        canvas.drawBitmap(slider, downX + getPaddingLeft(), getPaddingTop() + BORDER_WIDTH, null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float dx = event.getX() - getPaddingLeft();
        float dy = event.getY() - getPaddingBottom();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (dx > DEFAULT_WIDTH || dy > DEFAULT_HEIGHT
                        || dx < 0 || dy < 0) {
                    return false;
                } else {
                    isSlip = true;
                    nowX = dx;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                nowX = dx;
                break;

            case MotionEvent.ACTION_UP:
                isSlip = false;

                if (dx >= DEFAULT_WIDTH / 2) {
                    if (!nowStatus) {
                        nowStatus = true;
                        if (listener != null) {
                            listener.onChanged(CustomSwitch.this, nowStatus);
                        }
                    }
                    nowX = DEFAULT_WIDTH - SLIDER_SIZE;
                } else {
                    if (nowStatus) {
                        nowStatus = false;
                        if (listener != null) {
                            listener.onChanged(CustomSwitch.this, nowStatus);
                        }
                    }
                    nowX = 0;
                }

                break;
        }

        invalidate();
        return true;
    }

    private static float dp2px(float dp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    private static int dp2pxInt(float dp) {
        return (int) dp2px(dp);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;
    }

    public void setChecked(boolean checked) {
        if (checked) {
            nowX = bg_on.getWidth();
        } else {
            nowX = 0;
        }

        nowStatus = checked;
        invalidate();
    }

    /**
     * 回調接口
     */
    public interface OnChangeListener {

        public void onChanged(CustomSwitch mSwitch, boolean checkState);

    }
}