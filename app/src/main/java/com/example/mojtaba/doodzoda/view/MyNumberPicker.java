package com.example.mojtaba.doodzoda.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeProvider;
import android.widget.EditText;
import android.widget.NumberPicker;

public class MyNumberPicker extends NumberPicker {
    public MyNumberPicker(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return super.dispatchTrackballEvent(event);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        return super.dispatchHoverEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    @Override
    protected int computeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    @Override
    protected int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    protected int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public int getSolidColor() {
        return super.getSolidColor();
    }

    @Override
    public void setOnValueChangedListener(OnValueChangeListener onValueChangedListener) {
        super.setOnValueChangedListener(onValueChangedListener);
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        super.setOnScrollListener(onScrollListener);
    }

    @Override
    public void setFormatter(Formatter formatter) {
        super.setFormatter(formatter);
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean performLongClick() {
        return super.performLongClick();
    }

    @Override
    public boolean getWrapSelectorWheel() {
        return super.getWrapSelectorWheel();
    }

    @Override
    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        super.setWrapSelectorWheel(wrapSelectorWheel);
    }

    @Override
    public void setOnLongPressUpdateInterval(long intervalMillis) {
        super.setOnLongPressUpdateInterval(intervalMillis);
    }

    @Override
    public int getValue() {
        return super.getValue();
    }

    @Override
    public int getMinValue() {
        return super.getMinValue();
    }

    @Override
    public void setMinValue(int minValue) {
        super.setMinValue(minValue);
    }

    @Override
    public int getMaxValue() {
        return super.getMaxValue();
    }

    @Override
    public void setMaxValue(int maxValue) {
        super.setMaxValue(maxValue);
    }

    @Override
    public String[] getDisplayedValues() {
        return super.getDisplayedValues();
    }

    @Override
    public void setDisplayedValues(String[] displayedValues) {
        super.setDisplayedValues(displayedValues);
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return super.getTopFadingEdgeStrength();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return super.getBottomFadingEdgeStrength();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        return super.getAccessibilityNodeProvider();
    }

    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setDividerColor(int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(this, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void setTextColor(int color) {
        final int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = this.getChildAt(i);
            if (child instanceof EditText) {
                java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
                for (java.lang.reflect.Field pf : pickerFields) {
                    if (pf.getName().equals("mSelectorWheelPaint")) {
                        pf.setAccessible(true);
                        try {
                            ((Paint) pf.get(this)).setColor(color);
                            ((EditText) child).setTextColor(color);
                            this.invalidate();
                        } catch (IllegalAccessException e) {
                            Log.w("setNumPickerTextColor", e);
                        } catch (IllegalArgumentException e) {
                            Log.w("setNumPickerTextColor", e);
                        }
                    }
                }
            }
        }
    }
}
