package com.highway.study.coustomview.loadingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.highway.study.R;

/**
 * 自定义开关button
 * @author JH
 * @date 2017/3/28
 */
public class CoustomToggleButton extends View implements View.OnClickListener {

    private static final String TAG = "CoustomToggleButton";
    /** 背景图片*/
    private Bitmap bgBitmap;
    /** 滑动图片*/
    private Bitmap slidingBitmap;
    /** 距离左边最大距离*/
    private int slidLeftMax;
    private Paint paint;
    /** 滑动距离左边的距离*/
    private int slidLeft;
    /** 开或者关*/
    private boolean isOpen = false;

    public CoustomToggleButton(Context context) {
        this(context, null);
    }

    public CoustomToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoustomToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * inti view
     */
    private void initView() {
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button);
        slidLeftMax = bgBitmap.getWidth() - slidingBitmap.getWidth();
        paint = new Paint();
        paint.setAntiAlias(true);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(bgBitmap.getWidth(), bgBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bgBitmap, 0, 0, paint);
        canvas.drawBitmap(slidingBitmap, slidLeft, 0, paint);
    }

    private float startX;
    private float endX;
    private boolean isClick;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //起始按下的值
                endX = startX = event.getX();
                isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float lastX = event.getX();
                float destance = lastX - startX;
                slidLeft += destance;
                if (slidLeft < 0) {
                    slidLeft = 0;
                } else if (slidLeft > slidLeftMax) {
                    slidLeft = slidLeftMax;
                }
                invalidate();
                startX = event.getX();
                if (Math.abs(lastX - endX) > 5) {
                    isClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isClick) {
                    if (slidLeft > slidLeftMax / 2) {
                        isOpen = true;
                    } else {
                        isOpen = false;
                    }
                    flushView(); // 刷新
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (isClick) {
            isOpen = !isOpen;
            flushView();
        }
    }

    private void flushView() {
        if (isOpen) {
            slidLeft = slidLeftMax;
        } else {
            slidLeft = 0;
        }
        invalidate();
    }
}
