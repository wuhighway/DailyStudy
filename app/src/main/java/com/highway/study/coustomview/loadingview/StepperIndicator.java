package com.highway.study.coustomview.loadingview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.highway.study.R;

/**
 * Created by Administrator on 2017/9/13.
 */

public class StepperIndicator extends View {
    private float animProgress;
    private Paint lineDoneAnimatedPaint, linePaint;
    private float lineLength;
    private ObjectAnimator lineAnimator;

    public StepperIndicator(Context context) {
        super(context);
        initData();
    }

    public StepperIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public StepperIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        lineLength = w / 2;

        linePath = new Path();
        linePath.moveTo(w / 2 - w / 4, h / 2);
        linePath.lineTo(w / 2 + w / 4, h / 2);
    }

    private void initData() {
        float defaultLineStrokeWidth = getResources().getDimension(R.dimen.stpi_default_line_stroke_width);
        linePaint = new Paint();
        linePaint.setStrokeWidth(defaultLineStrokeWidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.parseColor("#b3bdc2"));
        linePaint.setAntiAlias(true);

        lineDoneAnimatedPaint = new Paint(linePaint);
        lineDoneAnimatedPaint.setColor(Color.parseColor("#00b47c"));

        lineDoneAnimatedPaint.setPathEffect(null);
    }

    Path linePath;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(linePath, linePaint);
        canvas.drawPath(linePath, lineDoneAnimatedPaint);
    }

    public void setAnimProgress(float animProgress) {
        this.animProgress = animProgress;
        lineDoneAnimatedPaint.setPathEffect(createPathEffect(lineLength, animProgress, 0.0f));
        invalidate();
    }

    public void initAnimation() {
        lineAnimator = ObjectAnimator.ofFloat(StepperIndicator.this, "animProgress", 0.0f, 1.0f);
        lineAnimator.setDuration(500);
        lineAnimator.setInterpolator(new DecelerateInterpolator());
        lineAnimator.start();
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset) {
        // Create a PathEffect to set on a Paint to only draw some part of the line
        return new DashPathEffect(new float[]{pathLength, pathLength}, Math.max(phase * pathLength, offset));
    }


}
