package com.example.demos.widget.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demos.R;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;

    protected List<List<View>> mAllViews = new ArrayList<List<View>>();
    protected List<Integer> mLineHeight = new ArrayList<Integer>();
    protected List<Integer> mLineWidth = new ArrayList<Integer>();
    private int mGravity;
    private List<View> lineViews = new ArrayList<>();
    private boolean isSmartSort = true;
    private List<View> smartSortViews;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mGravity = ta.getInt(R.styleable.TagFlowLayout_gravity, LEFT);
        ta.recycle();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("zltt", "onMeasure " + this);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        Log.d("zlt", "sizeHeight = " + sizeHeight + "," + this);
        // wrap_content
        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        int resultWidth = modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight();
        int resultHeight = modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom();
        Log.d("zlt", "before : resultHeight = " + resultHeight + "," + this);
        if (isSmartSort) {
            if (smartSortViews == null) {
                smartSortViews = getSmartSortViews(resultWidth);
            }
            height = 0;
            lineHeight = 0;
            lineWidth = 0;
            width = 0;
            for (int i = 0; i < cCount; i++) {
                View child = smartSortViews.get(i);

                if (child.getVisibility() == View.GONE) {
                    if (i == cCount - 1) {
                        width = Math.max(lineWidth, width);
                        height += lineHeight;
                    }
                    continue;
                }
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                int childWidth = child.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin
                        + lp.bottomMargin;

                if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                    width = Math.max(width, lineWidth);
                    lineWidth = childWidth;
                    height += lineHeight;
                    lineHeight = childHeight;
                } else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
            }
        }

        resultHeight = modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(resultWidth, resultHeight);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("zltt", "onLyaout " + this);
        mAllViews.clear();
        mLineHeight.clear();
        mLineWidth.clear();
        lineViews.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (isSmartSort) {
                if (smartSortViews == null) {
                    smartSortViews = getSmartSortViews(getMeasuredWidth());
                }
                child = smartSortViews.get(i);
            }
            if (child.getVisibility() == View.GONE) continue;
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                mLineWidth.add(lineWidth);

                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList<View>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);

        }
        mLineHeight.add(lineHeight);
        mLineWidth.add(lineWidth);
        mAllViews.add(lineViews);


        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            // set gravity
            int currentLineWidth = this.mLineWidth.get(i);
            switch (this.mGravity) {
                case LEFT:
                    left = getPaddingLeft();
                    break;
                case CENTER:
                    left = (width - currentLineWidth) / 2 + getPaddingLeft();
                    break;
                case RIGHT:
                    left = width - currentLineWidth + getPaddingLeft();
                    break;
            }

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
            }
            top += lineHeight;
        }

    }

    @NonNull
    private List<View> getSmartSortViews(int width) {
        int childCount = getChildCount();
        List<View> unsortChildViews = new ArrayList<>(childCount);
        List<View> sortChildViews = new ArrayList<>();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                unsortChildViews.add(getChildAt(i));
            }

            //因为每个item的margin都一样，所以在这儿一次性获取，避免反复计算
            MarginLayoutParams lp = (MarginLayoutParams) getChildAt(0)
                    .getLayoutParams();
            int childMargin = lp.leftMargin + lp.rightMargin;

            int parentWidth = width;

            while (sortChildViews.size() < childCount) {
                int n = 0;
                int m = 0;
                int count = unsortChildViews.size();
                int[] dp = new int[parentWidth + 1];
                int[][] state = new int[count][parentWidth + 1];

                if (getLastChildrenTotalWidth(unsortChildViews) <= parentWidth - childMargin * count) {
                    //如果剩余控件总宽度小于parent,就不排序
                    sortChildViews.addAll(unsortChildViews);
                    break;
                }

                for (n = 0; n < count; n++) {
                    View child = unsortChildViews.get(n);
                    int childW = child.getMeasuredWidth() + childMargin;
                    for (m = parentWidth; m >= childW; --m) {
                        int temp = dp[m - childW] + childW;
                        if (temp > dp[m]) {
                            dp[m] = temp;
                            state[n][m] = 1;
                        }
                    }
                }

                n = count;
                m = parentWidth;
                while (--n >= 0) {
                    if (state[n][m] == 1) {
                        sortChildViews.add(unsortChildViews.get(n));
                        View child = unsortChildViews.get(n);
                        int childW = child.getMeasuredWidth() + childMargin;
                        m -= childW;
                    }
                }

                for (View view : sortChildViews) {
                    if (unsortChildViews.contains(view)) {
                        unsortChildViews.remove(view);
                    }
                }
            }
        }
        return sortChildViews;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    /**
     * 是否启用智能排序
     * 智能排序会根据每个Tag的宽度合理布局，不会造成控件的浪费
     *
     * @param enable
     */
    public void enableSmartSort(boolean enable) {
        isSmartSort = enable;
    }

    private int getLastChildrenTotalWidth(List<View> childList) {
        int w = 0;
        for (View child : childList) {
            w += child.getMeasuredWidth();
        }
        return w;
    }
}
