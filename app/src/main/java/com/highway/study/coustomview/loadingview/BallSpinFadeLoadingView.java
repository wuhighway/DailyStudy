package com.highway.study.coustomview.loadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.highway.study.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JH
 * on 2017/2/6.
 */

public class BallSpinFadeLoadingView extends View {

    private static final String TAG = "BallSpinFadeLoadingView";
    /** 球的颜色*/
    private int mIndicatorColor;
    private Paint mPaint;
    /** view 默认大小*/
    public static final int DEFAULT_SIZE = 30;
    /** 是否创建动画*/
    private boolean mHasAnimation;
    /** 尺寸*/
    public static final float SCALE = 1.0f;
    /** alpha值*/
    public static final int ALPHA = 255;
    private List<Animator> mAnimators;

    float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    int[] alphas = new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};

    public BallSpinFadeLoadingView(Context context) {
        super(context);
        init(null, 0);
    }

    public BallSpinFadeLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BallSpinFadeLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    /**
     * init
     * @param attrs
     * @param defStyleAttr
     */
    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView);
        mIndicatorColor = a.getColor(R.styleable.LoadingView_indicator_color, Color.WHITE);
        a.recycle();
        mPaint = new Paint();
        mPaint.setColor(mIndicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure....");
        int width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 测量大小
     * @param defaultSize 默认大小
     * @param measureSpec measureSpec
     * @return
     */
    private int measureDimension(int defaultSize, int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw....");
        float radius = getWidth() / 10;
        for (int i = 0; i < 8; i++) {
            canvas.save();
            // 获取绘制小圆的原点
            Point point = circleAt(getWidth(), getHeight(), getWidth() / 2 - radius, i * (Math.PI / 4));
            // 以point为原点坐标
            canvas.translate(point.x, point.y);
            // 设置尺寸
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            // 设置透明度
            mPaint.setAlpha(alphas[i]);
            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout....");
        if (!mHasAnimation) {
            mHasAnimation = true;
            mAnimators = createAnimators();
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            super.setVisibility(visibility);
            if (visibility == GONE || visibility == INVISIBLE) {
                setAnimationStatus(AnimStatus.END);
            } else {
                setAnimationStatus(AnimStatus.START);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "onDetachedFromWindow....");
        setAnimationStatus(AnimStatus.END);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow....");
        setAnimationStatus(AnimStatus.START);
    }

    public void setAnimationStatus(AnimStatus status) {
        if (mAnimators == null) {
            return;
        }
        int count = mAnimators.size();
        for (int i = 0; i < count; i++) {
            Animator animator = mAnimators.get(i);
            boolean isRunning = animator.isRunning();
            switch (status) {
                case START:
                    if (!isRunning) {
                        animator.start();
                    }
                    break;
                case END:
                    if (isRunning) {
                        animator.end();
                    }
                    break;
                case CANCEL:
                    if (isRunning) {
                        animator.cancel();
                    }
                    break;
            }
        }
    }

    public enum AnimStatus {
        START, END, CANCEL
    }

    /**
     * 创建动画
     */
    public List<Animator> createAnimators() {
        List<Animator> animators = new ArrayList<>();
        // 延迟时间
        int[] delays = {0, 120, 240, 360, 480,
                600, 720, 840};
        for (int i = 0; i < 8; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1f, 0.4f, 1f);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnim.setStartDelay(delays[index]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 77, 255);
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnim.setStartDelay(delays[i]);
            alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphas[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            alphaAnim.start();
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }


    /**
     * 圆O的圆心为(a,b),半径为R,点A与到X轴的为角α.
     * 则点A的坐标为(a+R*cosα,b+R*sinα)
     *
     * @param width
     * @param height
     * @param radius
     * @param angle
     * @return
     */
    private Point circleAt(int width, int height, float radius, double angle) {
        float x = (float) (width / 2 + radius * (Math.cos(angle)));
        float y = (float) (height / 2 + radius * (Math.sin(angle)));
        return new Point(x, y);
    }


    final class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * dp to px
     *
     * @param dpValue
     * @return
     */
    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }
}
