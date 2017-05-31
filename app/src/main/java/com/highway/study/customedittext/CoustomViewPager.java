package com.highway.study.customedittext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自定义viewpager
 * @author JH
 * @date 2017/3/31
 */
public class CoustomViewPager extends ViewGroup {

    private GestureDetector detector;

    private Scroller scroller;

    public CoustomViewPager(Context context) {
        this(context, null);
    }

    public CoustomViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX, 0);
                return true;
            }
        });
        scroller = new Scroller(context);
    }

    /**
     * 当前页面的下标位置
     */
    private int currentIndex;
    float startX, currentX;

    float stX, stY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        boolean result = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stX = ev.getX();
                stY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endx = ev.getX();
                float endy = ev.getY();
                float distanceX = Math.abs(endx - stX);
                float distanceY = Math.abs(endy - stY);

                if (distanceX > distanceY && distanceX > 5) {
                    result = true;
                } else {
                    scrollToPager(currentIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event); // 将事件传递给手势识别
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录起始按下坐标
                currentX = startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
//                float cuX = event.getX();
//                float destence = cuX - currentX;
//                // 屏蔽非法值
//                if ( getScrollX() - destence  < 0) {
//                    destence = 0;
//                } else if (getScrollX() - destence > (getChildCount() - 1) * getWidth()){
//                    destence = 0;
//                }
//                scrollBy((int) -destence, 0);
//                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                // 结束时的坐标
                float endX = event.getX();
                int tempIndex = currentIndex;
                // 根据滑动的距离，获取当前的位置
                if (startX - endX > getWidth() / 3) {
                    tempIndex++;
                } else if (endX - startX > getWidth() / 3) {
                    tempIndex--;
                }
                scrollToPager(tempIndex);
                break;
        }
        return true;
    }

    /**
     * 根据滑动的距离移动到指定的子view
     * @param tempIndex
     */
    private void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        } else if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }

        currentIndex = tempIndex; // 当前位置
        int distanceX = currentIndex * getWidth() - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            float currx = scroller.getCurrX();
            scrollTo((int) currx, 0);
            postInvalidate(); // 调用该方法会调用 onDraw() computeScroll();
        }
    }

    /**
     * 设置子view在父view中的位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
