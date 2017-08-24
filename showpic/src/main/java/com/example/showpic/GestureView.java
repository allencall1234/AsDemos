package com.example.showpic;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

/**
 * 手势View
 */
public class GestureView extends RelativeLayout {

    private static final float MIN_SCALE = 0.6f;

    public GestureView(Context context) {
        this(context, null);
    }

    public GestureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final int mHeight = 500;
    private float mDisplacementX;
    private float mDisplacementY;
    private float mInitialTy;
    private float mInitialTx;
    private boolean mTracking;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                mDisplacementX = event.getRawX();
                mDisplacementY = event.getRawY();

                mInitialTy = getTranslationY();
                mInitialTx = getTranslationX();

                break;

            case MotionEvent.ACTION_MOVE:
                // get the delta distance in X and Y direction
                float deltaX = event.getRawX() - mDisplacementX;
                float deltaY = event.getRawY() - mDisplacementY;


                // set the touch and cancel event
                if (deltaY > 0 && (Math.abs(deltaY) > ViewConfiguration.get(getContext()).getScaledTouchSlop() * 2 && Math.abs(deltaX) < Math.abs(deltaY) / 2)
                        || mTracking) {

                    if (onSwipeListener != null) {
                        onSwipeListener.onSwiping(deltaY);
                    }
                    setBackgroundColor(Color.TRANSPARENT);

                    mTracking = true;

                    setTranslationY(mInitialTy + deltaY);
                    setTranslationX(mInitialTx + deltaX);

                    float mScale = 1 - deltaY / mHeight;
                    if (mScale < MIN_SCALE) {
                        mScale = MIN_SCALE;
                    }
                    setScaleX(mScale);
                    setScaleY(mScale);

                }
                if (deltaY < 0) {
                    setViewDefault();
                }

                break;

            case MotionEvent.ACTION_UP:

                if (mTracking) {
                    mTracking = false;
                    float currentTranslateY = getTranslationY();

//                    if (currentTranslateY > mHeight / 3) {
//                    if (onSwipeListener != null) {
//                        onSwipeListener.downSwipe();
//                    }
//                    break;
//                    }

                }
                setViewDefault();
                if (onSwipeListener != null) {
                    onSwipeListener.overSwipe();
                }
                break;
        }
        return false;
    }

    private void setViewDefault() {
        //恢复默认
        setAlpha(1);
        setTranslationX(0);
        setTranslationY(0);
        setScaleX(1);
        setScaleY(1);
        setBackgroundColor(Color.BLACK);
    }


    public interface OnSwipeListener {
        //向下滑动
        void downSwipe();

        //结束
        void overSwipe();

        //正在滑动
        void onSwiping(float y);
    }


    private OnSwipeListener onSwipeListener;

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }

}