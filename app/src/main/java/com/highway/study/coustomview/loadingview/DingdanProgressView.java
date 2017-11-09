package com.highway.study.coustomview.loadingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.highway.study.R;

/**
 * @author JH
 * @date 2017/10/9
 * 订单进度条
 */


public class DingdanProgressView extends View {

    private int width;
    private int height;
    private int progressWidth;
    private int progressHeight;
    private Bitmap okDrawable;
    private int okWidth;
    private int nomalWidth;
    private int maTop;
    private int distance;
    private int leftDistance;
    private Paint mPaint;
    private int progressDHeight;
    private int nomalColor = Color.parseColor("#D8D8D8");
    private int selectColor = Color.parseColor("#FC5638");
    private int cycleY;
    private int point = 1;
    private int present = 50;
    private int titleColor = Color.parseColor("#525252");
    private int timeColor = Color.parseColor("#999999");
    private int titleSize;
    private int timeSize;
    private int ydSize;
    private int singleWidth;
    private int textY;
    private Path singlePath;

    public DingdanProgressView(Context context) {
        super(context);
        init(context);
    }

    public DingdanProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DingdanProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        progressWidth = (width - leftDistance * 2 - okWidth - 3 * nomalWidth - 6 * distance) / 3;
        setMeasuredDimension(width, height);
    }

    private void init(Context context) {
        singlePath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        okDrawable = BitmapFactory.decodeResource(context.getResources(), R.drawable.ok);
        okWidth = dip2px(context, 21);
        nomalWidth = dip2px(context, 13);
        maTop = dip2px(context, 28);
        distance = dip2px(context, 5);
        height = dip2px(context, 123);
        leftDistance = dip2px(context, 32);
        progressHeight = dip2px(context, 2);
        singleWidth = dip2px(context, 7);
        cycleY = maTop + okWidth / 2;
        progressDHeight = cycleY - progressHeight / 2;
        titleSize = sp2px(14);
        timeSize = sp2px(10);
        ydSize = sp2px(12);
        textY = maTop + okWidth * 2;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // SUPPRESS CHECKSTYLE
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPoint0(canvas);
        drawPoint1(canvas);
        drawPoint2(canvas);
        drawPoint3(canvas);
        drawProgress1(canvas);
        drawProgress2(canvas);
        drawProgress3(canvas);
        drawTitle1(canvas);
        drawTitle2(canvas);
        drawTitle3(canvas);
        drawTitle4(canvas);
        drawYdTime(canvas);
        drawYdSuccessTime(canvas);
        drawYjkjTime(canvas);
        drawSingleYd(canvas);
        drawSinglePj(canvas);
        drawPJTime(canvas);
    }

    private void drawPoint0(Canvas canvas) {
        if (point == 1 && present < 100) {
//            Rect src = new Rect(leftDistance, maTop, leftDistance + okDrawable.getWidth(), maTop + okDrawable.getWidth());
            RectF des = new RectF(leftDistance, maTop, leftDistance + okWidth, maTop + okWidth);
            canvas.drawBitmap(okDrawable, null, des, mPaint);
        } else {
            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(leftDistance + nomalWidth / 2, cycleY, nomalWidth / 2, mPaint);
        }
    }

    private void drawPoint1(Canvas canvas) {
        if (point == 1 && present < 100) {
            mPaint.setColor(nomalColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(leftDistance + okWidth + distance + progressWidth + distance + nomalWidth / 2, cycleY, nomalWidth / 2, mPaint);
        } else if ((point == 1 && present == 100) || (point == 2 && present < 100)) {
            int left = leftDistance + nomalWidth + distance + progressWidth + distance;
            RectF des = new RectF(left, maTop, left + okWidth, maTop + okWidth);
            canvas.drawBitmap(okDrawable, null, des, mPaint);
        } else {
            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(leftDistance + nomalWidth + distance + progressWidth + distance + nomalWidth / 2, cycleY, nomalWidth / 2, mPaint);
        }
    }

    private void drawPoint2(Canvas canvas) {
        if ((point == 2 && present == 100) || (point == 3 && present < 100)) {
            int left = leftDistance + nomalWidth * 2 + progressWidth * 2 + distance * 4;
            RectF des = new RectF(left, maTop, left + okWidth, maTop + okWidth);
            canvas.drawBitmap(okDrawable, null, des, mPaint);
        } else if ((point == 2 && present < 100) || point < 2) {
            mPaint.setColor(nomalColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(leftDistance + okWidth + progressWidth * 2 + distance * 4 + nomalWidth / 2 + nomalWidth, cycleY, nomalWidth / 2, mPaint);
        } else if (point == 3 && present == 100) {
            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(leftDistance + progressWidth * 2 + distance * 4 + nomalWidth / 2 + nomalWidth * 2, cycleY, nomalWidth / 2, mPaint);
        }
    }

    private void drawPoint3(Canvas canvas) {
        if ((point == 3 && present == 100)) {
            int left = leftDistance + nomalWidth * 3 + progressWidth * 3 + distance * 6;
            RectF des = new RectF(left, maTop, left + okWidth, maTop + okWidth);
            canvas.drawBitmap(okDrawable, null, des, mPaint);
        } else if ((point == 3 && present < 100) || point < 3) {
            mPaint.setColor(nomalColor);
            mPaint.setStyle(Paint.Style.FILL);
            int cycleX = leftDistance + okWidth + progressWidth * 3 + distance * 6 + nomalWidth / 2 + nomalWidth * 2;
            canvas.drawCircle(cycleX, cycleY, nomalWidth / 2, mPaint);
        }
    }


    private void drawProgress1(Canvas canvas) {
        int top = 0;
        if (point == 1 && present < 100) {
            top = leftDistance + okWidth + distance;
            mPaint.setColor(nomalColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth
                    , progressDHeight + progressHeight, mPaint);
            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth * 0.5f
                    , progressDHeight + progressHeight, mPaint);
        } else {
            top = leftDistance + nomalWidth + distance;
            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth
                    , progressDHeight + progressHeight, mPaint);
        }
    }

    private void drawProgress2(Canvas canvas) {
        if (point == 1) {
            int top = leftDistance + okWidth + progressWidth + nomalWidth + distance * 3;
            mPaint.setColor(nomalColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth
                    , progressDHeight + progressHeight, mPaint);

        } else if (point == 2 && present < 100) {
            int top = leftDistance + okWidth + progressWidth + nomalWidth + distance * 3;
            mPaint.setColor(nomalColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth
                    , progressDHeight + progressHeight, mPaint);
            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth * 0.5f
                    , progressDHeight + progressHeight, mPaint);
        } else if ((point == 2 && present == 100) || point > 2) {
            int top = leftDistance + progressWidth + nomalWidth * 2 + distance * 3;
            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth
                    , progressDHeight + progressHeight, mPaint);
        }
    }

    private void drawProgress3(Canvas canvas) {
        if (point < 3) {
            int top = leftDistance + nomalWidth * 2 + okWidth + distance * 5 + progressWidth * 2;
            mPaint.setColor(nomalColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth
                    , progressDHeight + progressHeight, mPaint);
        } else if (point == 3 && point < 100) {
            int top = leftDistance + nomalWidth * 2 + okWidth + distance * 5 + progressWidth * 2;
            mPaint.setColor(nomalColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth
                    , progressDHeight + progressHeight, mPaint);

            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth * 0.5f
                    , progressDHeight + progressHeight, mPaint);
        } else if (point == 3 && point == 100) {
            int top = leftDistance + nomalWidth * 3 + distance * 5 + progressWidth * 2;
            mPaint.setColor(selectColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(top, progressDHeight, top + progressWidth
                    , progressDHeight + progressHeight, mPaint);
        }
    }



    private void drawTitle1(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(titleSize);
        if (point == 1 && present < 100) {
            mPaint.setColor(selectColor);
        } else {
            mPaint.setColor(titleColor);
        }
        int x = leftDistance + okWidth / 2;
        canvas.drawText("发起约单", x, textY, mPaint);
    }

    private void drawTitle2(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(titleSize);
        if ((point == 1 && present == 100) || (point == 2 && present < 100)) {
            mPaint.setColor(selectColor);
        } else {
            mPaint.setColor(titleColor);
        }
        int x = leftDistance + okWidth + progressWidth + distance * 2 + nomalWidth / 2;
        canvas.drawText("约单成功", x, textY, mPaint);
    }

    private void drawTitle3(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(titleSize);
        if ((point == 2 && present == 100) || (point == 3 && present < 100)) {
            mPaint.setColor(selectColor);
        } else {
            mPaint.setColor(titleColor);
        }
        int x = leftDistance + okWidth + progressWidth * 2 + distance * 4 + nomalWidth +  nomalWidth / 2;
        canvas.drawText("开奖", x, textY, mPaint);
    }

    private void drawTitle4(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(titleSize);
        if ((point == 3 && present == 100)) {
            mPaint.setColor(selectColor);
        } else {
            mPaint.setColor(titleColor);
        }
        int x = leftDistance + okWidth + progressWidth * 3 + distance * 6 + nomalWidth * 2 +  nomalWidth / 2;
        canvas.drawText("派奖", x, textY, mPaint);
    }

    private void drawSingleYd(Canvas canvas){
        float x = leftDistance + distance + okWidth + progressWidth * 0.35f - singleWidth / 2;
        mPaint.setColor(selectColor);
        mPaint.setStyle(Paint.Style.FILL);
        singlePath.moveTo(x, okWidth + progressHeight);
        singlePath.lineTo(x + singleWidth, okWidth +  progressHeight);
        singlePath.lineTo(x + (singleWidth / 2), okWidth + singleWidth + progressHeight);
        singlePath.close();
        mPaint.setTextSize(ydSize);
        canvas.drawPath(singlePath, mPaint);
        canvas.drawText("约单中", x + (singleWidth / 2), okWidth - progressHeight, mPaint);
    }

    private void drawSinglePj(Canvas canvas){
        float x = leftDistance + distance * 5 + okWidth + nomalWidth * 2 + progressWidth * 0.35f + progressWidth * 2;
        mPaint.setColor(selectColor);
        mPaint.setStyle(Paint.Style.FILL);
        singlePath.moveTo(x, okWidth + progressHeight);
        singlePath.lineTo(x + singleWidth, okWidth +  progressHeight);
        singlePath.lineTo(x + (singleWidth / 2), okWidth + singleWidth + progressHeight);
        singlePath.close();
        mPaint.setTextSize(ydSize);
        canvas.drawPath(singlePath, mPaint);
        canvas.drawText("派奖中", x + (singleWidth / 2), okWidth - progressHeight, mPaint);
    }

    private void drawYdTime(Canvas canvas) {
        int x = leftDistance + okWidth / 2;
        mPaint.setTextSize(timeSize);
        mPaint.setColor(timeColor);
        canvas.drawText("08-08", x, textY + okWidth / 2 + distance, mPaint);
        canvas.drawText("15:17:23", x, textY + okWidth + distance, mPaint);
    }
    private void drawYdSuccessTime(Canvas canvas) {
        int x = leftDistance + okWidth + progressWidth + distance * 2 + nomalWidth / 2;
        mPaint.setTextSize(timeSize);
        mPaint.setColor(timeColor);
        canvas.drawText("08-08", x, textY + okWidth / 2 + distance, mPaint);
        canvas.drawText("15:17:23", x, textY + okWidth + distance, mPaint);
    }
    private void drawYjkjTime(Canvas canvas) {
        int x = leftDistance + okWidth + progressWidth * 2 + distance * 4 + nomalWidth +  nomalWidth / 2;
        mPaint.setTextSize(timeSize);
        mPaint.setColor(timeColor);
        canvas.drawText("预计 08-08", x, textY + okWidth / 2 + distance, mPaint);
        canvas.drawText("15:17:34", x, textY + okWidth + distance, mPaint);
    }
    /**
     * 派奖时间
     * @param canvas
     */
    private void drawPJTime(Canvas canvas) {
        int x = leftDistance + okWidth + progressWidth * 3 + distance * 6 + nomalWidth * 2 +  nomalWidth / 2;
        mPaint.setTextSize(timeSize);
        mPaint.setColor(timeColor);
        canvas.drawText("预计 08-08", x, textY + okWidth / 2 + distance, mPaint);
        canvas.drawText("15:17:34", x, textY + okWidth + distance, mPaint);
    }

    public void setStateProgressInfo(String strpoint, String strpercent, String des) {
        point = 1;
        present = 0;
        try {
            point = Integer.valueOf(strpoint);
            present = Integer.valueOf(strpercent);
        } catch (Exception e) {
            point = 1;
            present = 0;
        }
        invalidate();
    }

    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}

