package com.highway.study.coustomview.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by JH
 * on 2017/2/6.
 * 加载动画
 */

public class LoadingView extends View {
    /**
     * 红色
     */
    private static final int RED = 0xffff5a54;
    /**
     * 蓝色
     */
    private static final int BLUE = 0xff00add3;
    /**
     * context
     */
    private Context mContext;

    /**
     * paint
     **/
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 动画状态
     **/
    private float mBallState = 0;

    /**
     * 最小state值
     **/
    final float mMinVal = 0;
    /**
     * 最大state值
     **/
    final float mMaxVal = 4;

    /**
     * 动画持续时间
     **/
    final int mAnimationDuration = 800;
    /**
     * 正常球大小
     */
    private static final int BALL_SIZE = 10;
    /**
     * 大球大小
     */
    private static final int BALL_SIZE_BIG = 15;
    /**
     * 小球大小
     */
    private static final int BALL_SIZE_SMALL = 8;
    /**
     * 位移距离
     */
    private static final int MOVE_WIDTH = 50;
    /**
     * 默认球半径
     **/
    int mBallSize = BALL_SIZE;
    /**
     * 球在前面中间半径
     **/
    int mFBallSize = BALL_SIZE_BIG;
    /**
     * 球在后面中间半径
     **/
    int mBBallSize = BALL_SIZE_SMALL;
    /**
     * 位移距离
     */
    int moveWidth = MOVE_WIDTH;

    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHight;
    Rect r = new Rect();

    private ValueAnimator mAnimator;

    public LoadingView(Context context) {
        super(context);
        initData(context);
        initAnimation();
        mBallState = 0;
        mPaint.setStyle(Paint.Style.FILL);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initAnimation();
        mBallState = 0;
        mPaint.setStyle(Paint.Style.FILL);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initAnimation();
        mBallState = 0;
        mPaint.setStyle(Paint.Style.FILL);
    }


    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // SUPPRESS CHECKSTYLE
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHight = h;
        r.set(getLeft(), getTop(), getRight(), getBottom());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] redProperty = getBallProperty(mBallState);
        float[] blueProperty = getBallProperty(mBallState + 2);
//        canvas.clipRect(r);
        if (mBallState < 2) {
            mPaint.setColor(BLUE);
            canvas.drawCircle(blueProperty[1], blueProperty[2], blueProperty[0], mPaint);

            mPaint.setColor(RED);
            canvas.drawCircle(redProperty[1], redProperty[2], redProperty[0], mPaint);
        } else {
            mPaint.setColor(RED);
            canvas.drawCircle(redProperty[1], redProperty[2], redProperty[0], mPaint);

            mPaint.setColor(BLUE);
            canvas.drawCircle(blueProperty[1], blueProperty[2], blueProperty[0], mPaint);
        }
    }

    /**
     * 初始化数据
     *
     * @param ct ct
     */
    private void initData(Context ct) {
        mBallSize = dip2px(ct, BALL_SIZE);
        mFBallSize = dip2px(ct, BALL_SIZE_BIG);
        mBBallSize = dip2px(ct, BALL_SIZE_SMALL);
        moveWidth = dip2px(ct, MOVE_WIDTH);
    }

    private void initAnimation() {
        mAnimator = ValueAnimator.ofFloat(mMinVal, mMaxVal);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(60);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setDuration(mAnimationDuration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setBallState(value);
            }
        });
    }


    /**
     * 根据状态值，获取球的属性
     */
    private float[] getBallProperty(float value) {
        float state = value % 4;
        float ballSize = mBallSize;
        float x;
        float y;
        // 一共有四种临界状态， 0初始状态左右两边，1一前一后，2两个球调换位置左右两边，3两个球调换位置一前一后，4恢复到初始状态
        if (state >= 0 && state < 1) { // 前面球的半径逐渐变大，变道最大求的半径大小
            ballSize = mBallSize + (mFBallSize - mBallSize) * state;
        } else if (state >= 1 && state < 2) { // 前面的球的半径逐渐变小回复到正常大小
            ballSize = mFBallSize - (mFBallSize - mBallSize) * (state - 1);
        } else if (state >= 2 && state < 3) { // 后面的球的半径逐渐变小会变成最小球的半径
            ballSize = mBallSize + (mBBallSize - mBallSize) * (state - 2);
        } else if (state >= 3 && state < 4) { // 后面的球的半径逐渐由最小的半径变成正常大小
            ballSize = mBBallSize + (mBallSize - mBBallSize) * (state - 2);
        }

        float center = mWidth / 2;
        // 获取球的位移
        if (state < 2) {
            x = moveWidth / 2 * state + center - moveWidth / 2;
        } else {
            x = -moveWidth / 2 * state + center + moveWidth * 3 / 2;
        }

        y = mHight / 2;

        return new float[]{ballSize, x, y};
    }

    /**
     * 设置动画状态
     *
     * @param state 状态
     */
    public void setBallState(float state) {
        mBallState = state % mMaxVal;
        postInvalidate();
    }

    public void start() {
        if (!mAnimator.isRunning()) {
            mAnimator.start();
        }
    }


    public void stop() {
        if (mAnimator.isRunning()) {
            mAnimator.end();
        }
    }
}
