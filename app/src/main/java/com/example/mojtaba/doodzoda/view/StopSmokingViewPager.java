package com.example.mojtaba.doodzoda.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class StopSmokingViewPager extends ViewPager {

    private boolean pagingEnabled;

    public StopSmokingViewPager(Context context) {
        super(context);
    }

    public StopSmokingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.pagingEnabled = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }

        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onInterceptTouchEvent(ev);

    }

    public void setPagingEnabled(boolean state) {
        this.pagingEnabled = state;
    }
}
