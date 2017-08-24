package com.highway.study.coustomview.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

import com.highway.study.util.Utility;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MatchTechniqueView extends View {
    private Paint mPaint;
    private int centeryDistance;
    private int itemWidth;
    private int itemHeight;
    private int textColor;
    private int shuColor;
    private int itemNoColor;
    private int strongColor;
    private int weakColor;
    private int width;
    private int height;
    private int textSize;
    private float textDeafultWidth;
    private int shuWidth;
    private Context context;
    private FootballTeamPkData data;

    public MatchTechniqueView(Context context) {
        this(context, null);
    }

    public MatchTechniqueView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatchTechniqueView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        mPaint = new Paint();
        centeryDistance = Utility.dip2px(context, 3);
        itemWidth = Utility.dip2px(context, 104);
        itemHeight = Utility.dip2px(context, 6);
        textColor = Color.parseColor("#737373");
        shuColor = Color.parseColor("#525252");
        strongColor = Color.parseColor("#3465A6");
        weakColor = Color.parseColor("#96A1B4");
        itemNoColor = Color.parseColor("#EFEFF4");
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
        mPaint.setTextSize(textSize);
        textDeafultWidth = mPaint.measureText("场均进球啥");
        shuWidth = Utility.dip2px(context, 10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

//    private int getTextWidth(String str, Paint paint) {
//        Rect rect = new Rect();
//        mPaint.getTextBounds(str, 0, str.length(), rect);
//        int width = rect.width();//文字宽
//        int height = rect.height();//文字高
//        return width;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制中间的文字
        if (data == null)
            return;
        drawCenter(canvas, data.getTitle());
        drawLeftRect(canvas);
        drawRightRect(canvas);
        drawLeftNum(canvas, TextUtils.isEmpty(data.getHvalue()) ? "0" : data.getHvalue());
        drawRightNum(canvas, TextUtils.isEmpty(data.getGvalue()) ? "0" : data.getGvalue());

        float maxLength = zhuValue + keValue;
        maxLength = maxLength > 0 ? maxLength : 1;//避免0+0=0；除数为0的情况

        drawLeftProgressRect(canvas, zValue / maxLength, getPaintColor(zhuValue, keValue));
        drawRightProgressRect(canvas, kValue / maxLength, getPaintColor(keValue, zhuValue));
    }

    public int getPaintColor(float leftValue, float rightValue) {
        if (leftValue > 0 && rightValue > 0 && leftValue == rightValue) {
            return strongColor;
        }
        return leftValue > rightValue ? strongColor : weakColor;
    }


    private void drawRightRect(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(itemNoColor);
        float left = width / 2 + centeryDistance + textDeafultWidth / 2;
        float top = height / 2 - itemHeight / 2;
        float right = width / 2 + centeryDistance + textDeafultWidth / 2 + itemWidth;
        float botton = height / 2 + itemHeight / 2;
        canvas.drawRect(left, top, right, botton, mPaint);

    }

    private void drawLeftRect(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(itemNoColor);
        float left = width / 2 - centeryDistance - textDeafultWidth / 2 - itemWidth;
        float top = height / 2 - itemHeight / 2;
        float right = width / 2 - centeryDistance - textDeafultWidth / 2;
        float botton = height / 2 + itemHeight / 2;
        canvas.drawRect(left, top, right, botton, mPaint);
    }

    private void drawLeftProgressRect(Canvas canvas, float pre, int color) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        float preWidth = itemWidth * pre;
        float left = width / 2 - centeryDistance - textDeafultWidth / 2 - preWidth;
        float botton = height / 2 + itemHeight / 2;
        float top = height / 2 - itemHeight / 2;
        float right = width / 2 - centeryDistance - textDeafultWidth / 2;
        canvas.drawRect(left, top, right, botton, mPaint);
    }

    private void drawRightProgressRect(Canvas canvas, float pre, int color) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        float preWidth = itemWidth * pre;
        float left = width / 2 + centeryDistance + textDeafultWidth / 2;
        float top = height / 2 - itemHeight / 2;
        float right = width / 2 + centeryDistance + textDeafultWidth / 2 + preWidth;
        float botton = height / 2 + itemHeight / 2;
        canvas.drawRect(left, top, right, botton, mPaint);
    }


    private void drawCenter(Canvas canvas, String text) {
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float baselineY = height / 2 - (mPaint.descent() - mPaint.ascent()) / 2 - mPaint.ascent();
        canvas.drawText(text, width / 2, baselineY, mPaint);
    }

    private void drawLeftNum(Canvas canvas, String num) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(shuColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float textWidth = mPaint.measureText("129");
        float left = width / 2 - centeryDistance - textDeafultWidth / 2 - itemWidth - shuWidth;
        float baselineY = height / 2 - (mPaint.descent() - mPaint.ascent()) / 2 - mPaint.ascent();
        canvas.drawText(num, left - textWidth / 2, baselineY, mPaint);
    }

    private void drawRightNum(Canvas canvas, String num) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(shuColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float textWidth = mPaint.measureText("129");
        float left = width / 2 + centeryDistance + textDeafultWidth / 2 + itemWidth + shuWidth;
        float baselineY = height / 2 - (mPaint.descent() - mPaint.ascent()) / 2 - mPaint.ascent();
        canvas.drawText(num, left + textWidth / 2, baselineY, mPaint);
    }

    Float zhuValue;
    Float keValue;

    Float zValue;
    Float kValue;
    public void setTeachData(FootballTeamPkData data) {
        if (data != null) {
            this.data = data;

            try {
                if (data.getHvalue().endsWith("%")) {
                    String aff = data.getHvalue().split("%")[0];
                    try {
                        zhuValue = Float.parseFloat(aff);
                    } catch (NumberFormatException e) {
                        zhuValue = 0f;
                    }
                } else {
                    zhuValue = Float.parseFloat(data.getHvalue());
                }
            } catch (NumberFormatException e) {
                zhuValue = 0f;
            }
            try {
                if (data.getGvalue().endsWith("%")) {
                    String aff = data.getGvalue().split("%")[0];
                    try {
                        keValue = Float.parseFloat(aff);
                    } catch (NumberFormatException e) {
                        keValue = 0f;
                    }
                } else {
                    keValue = Float.parseFloat(data.getGvalue());
                }
            } catch (NumberFormatException e) {
                keValue = 0f;
            }
            startAnimation();
            startRAnimation();
        }
    }

    private void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, zhuValue);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                zValue = (Float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    private void startRAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, keValue);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                kValue = (Float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
}
