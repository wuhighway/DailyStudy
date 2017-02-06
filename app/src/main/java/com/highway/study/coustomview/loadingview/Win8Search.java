package com.highway.study.coustomview.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by JH
 * on 2017/2/6.
 */

public class Win8Search extends View {
    /** 画笔*/
    private Paint mPaint;
    /** 路径*/
    private Path mPath;
    /** 路径测绘*/
    private PathMeasure mPathMeasure;
    /** 屏幕的宽度高度*/
    private int mWidth, mHeight;
    /** valueanimator*/
    private ValueAnimator valueAnimator;
    /** 动画的进度值*/
    private float animationValue;

    public Win8Search(Context context) {
        this(context, null);
    }

    public Win8Search(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        valueAnimator.start();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE); // 设置为空心
        mPaint.setStrokeWidth(15); // 宽度
        mPaint.setColor(Color.RED); // 颜色
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 设置画笔为园笔
        mPaint.setAntiAlias(true); // 抗锯齿
        mPath = new Path(); // 路径
        RectF rect = new RectF(-100, -100, 100, 100); // 测定圆弧的范围
        mPath.addArc(rect, -90, 359.9f); // 设置路径范围，起始角度，终止角度
        mPathMeasure = new PathMeasure(mPath, false); // 初始化要截取的路径

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(2500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE); // 设置动画播放模式
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2); // 平移画布
        Path dst = new Path();
        if (animationValue >= 0.95) { // 当接近结束时补画一个点
            canvas.drawPoint(0, -150, mPaint);
        }
        int num = (int) (animationValue / 0.05);
        float s, y, x;
        switch (num) {
            default:
            case 3:
                x = animationValue - 0.15f * (1 - animationValue);
                s = mPathMeasure.getLength();
                y = -s * x * x + 2 * s * x;
                mPathMeasure.getSegment(y, y + 1, dst, true);
            case 2:
                x = animationValue - 0.10f * (1 - animationValue);
                s = mPathMeasure.getLength();
                y = -s * x * x + 2 * s * x;
                mPathMeasure.getSegment(y, y + 1, dst, true);
            case 1:
                x = animationValue - 0.05f * (1 - animationValue);
                s = mPathMeasure.getLength();
                y = -s * x * x + 2 * s * x;
                mPathMeasure.getSegment(y, y + 1, dst, true);
            case 0:
                x = animationValue;
                s = mPathMeasure.getLength();
                y = -s * x * x + 2 * s * x;
                mPathMeasure.getSegment(y, y + 1, dst, true);
                break;
        }
        canvas.drawPath(dst, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public void stopAnimation(){
        valueAnimator.cancel();
    }

    public void startAnimation() {
        valueAnimator.start();
    }
}
