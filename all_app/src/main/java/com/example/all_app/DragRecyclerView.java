package com.example.all_app;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 524202 on 2017/8/30.
 */

public class DragRecyclerView extends RecyclerView {

    private boolean outBound = false;
    private float distance;
    private float firstPos;

    public DragRecyclerView(Context context) {
        super(context);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL)
                && outBound) {
            outBound = false;
        }

        outBound = detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }


    GestureDetectorCompat detector = new GestureDetectorCompat(getContext(), new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Rect localR = new Rect();
            boolean local = getLocalVisibleRect(localR);

            Rect globalR = new Rect();
            boolean global = getGlobalVisibleRect(globalR);

            Log.d("zlt", "local = " + local + ",localRect = " + localR);
            Log.d("zlt", "global = " + global + ",globalRect = " + globalR);
            View firstView = getChildAt(0);
            Rect childR = new Rect();
            boolean child = getChildVisibleRect(firstView, childR, null);
            Log.d("zlt", "child = " + child + ",childRect = " + childR);

            if (!outBound) {
                firstPos = e2.getRawY();
            }

            if (childR.top >= 0) {
                distance += distanceY;
                scrollTo(0, (int) (distance / 2));
                return true;
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    });
}
