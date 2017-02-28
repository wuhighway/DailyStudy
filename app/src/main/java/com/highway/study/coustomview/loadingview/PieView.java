package com.highway.study.coustomview.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.highway.study.coustomview.loadingview.bean.PieData;

import java.util.List;

/**
 * Created by JH
 * on 2017/2/28.
 */

public class PieView extends View {
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private List<PieData> pieDatas;
    private Paint mPaint;
    private float mStartAngle = 0;
    private int mWidth, mHight;
    public PieView(Context context) {
        this(context, null);

    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

//    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public PieView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pieDatas == null) {
            return;
        }
        float mCurrentAngle = mStartAngle;
        float r = (float) (Math.min(mWidth, mHight) / 2 * 0.8);
        canvas.translate(mWidth / 2, mHight / 2);
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < pieDatas.size(); i++) {
            PieData pie = pieDatas.get(i);
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rectF, mCurrentAngle, pie.getAngle(), true, mPaint);
            mCurrentAngle += pie.getAngle();
        }
    }

    public void setStartAngle(int angle) {
        mStartAngle = angle;
        invalidate();
    }

    public void setData(List<PieData> datas) {
        this.pieDatas = datas;
        initData(datas);
        invalidate();
    }

    private void initData(List<PieData> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        int sunValue = 0;
        for (int i = 0; i < datas.size(); i++) {
            PieData pie = datas.get(i);
            sunValue += pie.getValue();
            int j = i % mColors.length;       //设置颜色
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < datas.size(); i++) {
            PieData pie = datas.get(i);
            float percentage = pie.getValue() / sunValue;   // 百分比
            float angle = percentage * 360;                 // 对应的角度
            pie.setPercentage(percentage);                  // 记录百分比
            pie.setAngle(angle);                            // 记录角度大小
            sumAngle += angle;
            Log.i("angle", "" + pie.getAngle());
        }

    }
}
