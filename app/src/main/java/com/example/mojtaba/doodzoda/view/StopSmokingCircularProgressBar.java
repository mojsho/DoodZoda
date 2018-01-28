package com.example.mojtaba.doodzoda.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.mojtaba.doodzoda.R;

public class StopSmokingCircularProgressBar extends View {

    private float strokeWidth = 4;
    private float progress = 0;
    private int min = 0;
    private int max = 100;
    private int duration = 500;

    private int startAngle = 90;
    private int color;
    private RectF rectF;
    private Paint backgroundPaint;
    private Paint foregroundPaint;

    public StopSmokingCircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
        backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        backgroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setStrokeWidth(strokeWidth);
        invalidate();
        requestLayout();
    }

    public void setMin(int min) {
        this.min = min;
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public float getProgress() {
        return progress;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getColor() {
        return color;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        invalidate();
    }

    public int getDuration() {
        return duration;
    }

    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, 0, 0);

        try {
            strokeWidth = typedArray.getDimension(R.styleable.CircularProgressBar_progressThickness, strokeWidth);
            progress = typedArray.getFloat(R.styleable.CircularProgressBar_progress, progress);
            max = typedArray.getInteger(R.styleable.CircularProgressBar_max, max);
            min = typedArray.getInteger(R.styleable.CircularProgressBar_min, min);
            color = typedArray.getColor(R.styleable.CircularProgressBar_progressColor, color);
        } finally {
            typedArray.recycle();
        }

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);

        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(color);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(rectF, backgroundPaint);
        float angle = 360 * progress / max;
        canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        final int min = Math.min(height, width);

        setMeasuredDimension(min, min);

        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void setProgressWithAnimation(float progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }
}
