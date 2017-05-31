package com.dailystudy.touchevent;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * @author JH
 * @date 2017/4/18
 */


public class SlideLayout extends FrameLayout {
    private static final String TAG = "SlideLayout";
    private View contentView;
    private View menuView;
    private int menuLenth;
    private int contentLenth;
    private int contentHight;

    public SlideLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.e(TAG, "SlideLayout onInterceptTouchEvent " + TouchEventUtil.getTouchAction(event.getAction()));
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.按下记录坐标
                firstX = startX = event.getX();
                Log.e(TAG, "SlideLayout-onTouchEvent-ACTION_DOWN");
                if (onStateChangeListenter != null) {
                    onStateChangeListenter.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "SlideLayout-onTouchEvent-ACTION_MOVE");
                //2.记录结束值
                float endX = event.getX();
                float endY = event.getY();
                //3.计算偏移量
                float distanceX = endX - startX;

                startX = event.getX();
                //在X轴和Y轴滑动的距离
                float DX = Math.abs(endX - firstX);
                if (DX > 8) {
                    intercept = true;
                }


                break;
            case MotionEvent.ACTION_UP:
                break;
        }


        return intercept;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "SlideLayout dispatchTouchEvent " + TouchEventUtil.getTouchAction(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        menuLenth = menuView.getMeasuredWidth();
        contentLenth = contentView.getMeasuredWidth();
        contentHight = contentView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(contentLenth, 0, contentLenth + menuLenth, contentHight);
    }

    private float startX;
    private float startY;
    private float firstX;
    private float firstY;
    private Scroller scroller;

    private void init(Context context) {
        scroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "SlideLayout onTouchEvent " + TouchEventUtil.getTouchAction(event.getAction()));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (onStateChangeListenter != null)
                    onStateChangeListenter.onDown(this);
                firstX = startX = event.getX();
                firstY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                float currenty = event.getY();
                int destanceX = (int) (currentX - startX);
                int todesX = getScrollX() - destanceX;
                if (todesX < 0) {
                    todesX = 0;
                } else if (todesX > menuLenth) {
                    todesX = menuLenth;
                }
                Log.e(TAG, "活动的点" + todesX);
                scrollTo(todesX, getScrollY());
                startX = event.getX();

                //在X轴和Y轴滑动的距离
                float DX = Math.abs(currentX - firstX);
                float DY = Math.abs(currenty - firstY);
                if (DX > DY && DX > 8) {
                    //水平方向滑动
                    //响应侧滑
                    //反拦截-事件给SlideLayout
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                int distanceX = getScrollX();
                if (distanceX < menuLenth / 2) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
        }
        return true;
    }

    public void openMenu() {
        if (onStateChangeListenter != null)
            onStateChangeListenter.onOpen(this);
        int distance = menuLenth - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distance, getScrollY());
        invalidate();
    }

    public void closeMenu() {
        if (onStateChangeListenter != null)
            onStateChangeListenter.onClose(this);
        int distance = 0 - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distance, getScrollY());
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 监听SlideLayout状态的改变
     */
    public interface OnStateChangeListenter {
        void onClose(SlideLayout layout);

        void onDown(SlideLayout layout);

        void onOpen(SlideLayout layout);
    }

    private OnStateChangeListenter onStateChangeListenter;

    /**
     * 设置SlideLayout状态的监听
     *
     * @param onStateChangeListenter
     */
    public void setOnStateChangeListenter(OnStateChangeListenter onStateChangeListenter) {
        this.onStateChangeListenter = onStateChangeListenter;
    }
}
