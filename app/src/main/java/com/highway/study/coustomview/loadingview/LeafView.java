package com.highway.study.coustomview.loadingview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;

import com.highway.study.R;

/**
 * Created by JH
 * on 2017/3/1.
 */

public class LeafView extends View {
    private static final String TAG = "LeafView";
    private int routing = 0;
    // 默认宽高
    private int mDefaultWidth = 300;
    private int mDefaultHeight = 60;
    // 淡白色
    private static final int WHITE_COLOR = 0xfffde399;
    // 橙色
    private static final int ORANGE_COLOR = 0xffffa800;
    private static final int TOTAL_PROGRESS = 100;          //默认进度最大值

    //外边框相关
    private Bitmap mOuterBitmap, mLeafBitmap;
    private int mOuterWidth, mOuterHeight;
    private Rect mOuterSrcRect, mOuterDestRect;
    private Resources mResources;
    private Paint mBitmapPaint, mOrangePaint;
    private int mCurrentProgress = 40;                           //当前进度
    private int mArcRadius;                                 //弧形半径
    private int mLeftMargin, mRightMargin;                  //用于控制进度条外边距
    private int mProgressWidth;                             //当前进度条的宽度
    private int mCurrentProgressPosition;                   //当前所绘制部分进度条的位置
    private RectF mWhiteRectF, mOrangeRectF, mArcRectF;     //区域
    private int mArcRightLocation;                          //arc的右上角x坐标，也是矩形x坐标的起始点

    private float mLeafBitmapWidth, mLeafBitmapHeight;      //叶子图片宽
    private float mLeafPaintWidth, mLeafPaintHeight;        //叶子绘制时宽高
    public LeafView(Context context) {
        this(context, null);
    }

    public LeafView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        initResources(context);
        initBitmap();
        initPaint();
    }
     /**
     * 初始化资源
     *
     * @param context 上下文
     */
    private void initResources(Context context) {
        mResources = getResources();
        mDefaultWidth = UiUtils.dipToPx(context, mDefaultWidth);
        mDefaultHeight = UiUtils.dipToPx(context, mDefaultHeight);
    }

    private void initPaint() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setColor(ORANGE_COLOR);
    }

    private void initBitmap() {

        mLeafBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf_xxhdpi)).getBitmap();
        mLeafBitmapWidth = mLeafBitmap.getWidth();
        mLeafBitmapHeight = mLeafBitmap.getHeight();
        mOuterBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.outer_xxhdpi)).getBitmap();
        mOuterWidth = mOuterBitmap.getWidth();
        mOuterHeight = mOuterBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽高测量值

        int measureWidth = getMeasureWidth(widthMeasureSpec);
        int measureHeight = getMeasureHeight(heightMeasureSpec);
        //按比例重置宽高 按小一边的取值 (宽 : 高 = 300 : 60)
        measureWidth = measureWidth > measureHeight * 5 ? measureHeight * 5 : measureWidth;
        measureHeight = measureWidth / 5;
        //自定义宽高
        setMeasuredDimension(measureWidth, measureHeight);
    }


    /**
     *
     * @param heightMeasureSpec
     * @return
     */
    private int getMeasureHeight(int heightMeasureSpec) {
        int height = 0;
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mDefaultHeight;
        }
        return height;
    }

    /**
     *
     * @param widthMeasureSpec
     * @return
     */
    private int getMeasureWidth(int widthMeasureSpec) {
        int width = 0;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mDefaultWidth;
        }
        return width;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int mTotalWidth = w > h * 5 ? h * 5 : w;
        int mTotalHeight = mTotalWidth / 5;

        mLeftMargin = mTotalHeight / 12;
        mRightMargin = mTotalHeight / 2;
        mProgressWidth = mTotalWidth - mLeftMargin - mRightMargin;
        mArcRadius = (mTotalHeight - 2 * mLeftMargin) / 2;
        routing = mArcRadius * 7;
        Log.e(TAG, "半径 = " +mArcRadius);
        mOuterSrcRect = new Rect(0, 0, mOuterWidth, mOuterHeight);
        mOuterDestRect = new Rect(0, 0, mTotalWidth, mTotalHeight);

        mArcRightLocation = mLeftMargin + mArcRadius;
        mArcRectF = new RectF(mLeftMargin, mLeftMargin, mLeftMargin + mArcRadius * 2, mTotalHeight - mLeftMargin);
        mOrangeRectF = new RectF(mLeftMargin + mArcRadius, mLeftMargin, mCurrentProgressPosition,
                mTotalHeight - mLeftMargin);

        //叶子相关
        mLeafPaintWidth = (float) mTotalHeight / 3;
        mLeafPaintHeight = (float) mTotalHeight / 6;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgress(canvas);
        drawLeafs(canvas);
        canvas.drawBitmap(mOuterBitmap, mOuterSrcRect, mOuterDestRect, mBitmapPaint);
        postInvalidate();
    }

    private void drawLeafs(Canvas canvas) {
        canvas.save();
        routing -= 2;
        //通过Matrix控制叶子的旋转
        Matrix matrix = new Matrix();
        //缩放
        float scaleX = mLeafPaintWidth / mLeafBitmapWidth;
        float scaleY = mLeafPaintHeight / mLeafBitmapHeight;
        matrix.postScale(scaleX, scaleY);
        //位移
        float transX = mLeftMargin + routing;
        float transY = mLeftMargin * 2;
        matrix.postTranslate(transX, transY);
        canvas.drawBitmap(mLeafBitmap, matrix, mBitmapPaint);
        canvas.restore();
    }

    private void drawProgress(Canvas canvas) {
        if (mCurrentProgress >= TOTAL_PROGRESS) {
            mCurrentProgress = TOTAL_PROGRESS;
        }
        // 获取当前进度位置
        mCurrentProgressPosition = mProgressWidth * mCurrentProgress / TOTAL_PROGRESS;

        // 当进度条在圆弧内
        if (mCurrentProgressPosition <= mArcRadius) {

            //单边角度
            int angle = (int) Math.toDegrees(Math.acos((mArcRadius - mCurrentProgressPosition) /
                    (float) mArcRadius));
            //起始位置
            int startAngle = 180 - angle;
            //扫过的角度
            int sweepAngle = 2 * angle;
//            canvas.drawRect(mArcRectF, mOrangePaint);
            canvas.drawArc(mArcRectF, startAngle, sweepAngle, false, mOrangePaint);
        } else {

            canvas.drawArc(mArcRectF, 90, 180, false, mOrangePaint);
            mOrangeRectF.left = mArcRightLocation;
            mOrangeRectF.right = mCurrentProgressPosition;
            canvas.drawRect(mOrangeRectF, mOrangePaint);
        }
    }


    public void setProgress(int progress) {
        mCurrentProgress = progress;

        postInvalidate();
    }
}
