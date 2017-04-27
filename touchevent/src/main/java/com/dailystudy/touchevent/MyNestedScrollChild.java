package com.dailystudy.touchevent;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author JH
 * @date 2017/4/26
 */


public class MyNestedScrollChild extends LinearLayout implements NestedScrollingChild {
    private NestedScrollingChildHelper mScrollingChildHelper;
    private final int[] offset = new int[2];
    private final int[] consumed = new int[2];
    private int lastY;
    private int showHeight;

    public MyNestedScrollChild(Context context) {
        super(context);
    }

    public MyNestedScrollChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //第一次测量，因为布局文件中高度是wrap_content，因此测量模式为ATMOST，即高度不能超过父控件的剩余空间
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        showHeight = getMeasuredHeight();

        //第二次测量，对高度没有任何限制，那么测量出来的就是完全展示内容所需要的高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) (e.getRawY());
                int dy = y - lastY;
                lastY = y;

                if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) //如果找到了支持嵌套滚动的父类
                        && dispatchNestedPreScroll(0, dy, consumed, offset)) {//父类进行了一部分滚动

                    int remain = dy - consumed[1];//获取滚动的剩余距离
                    if (remain != 0) {
                        scrollBy(0, -remain);
                    }

                } else {
                    scrollBy(0, -dy);
                }
        }

        return true;
    }

    //scrollBy内部会调用scrollTo
    //限制滚动范围
    @Override
    public void scrollTo(int x, int y) {
        int MaxY = getMeasuredHeight() - showHeight;
        if (y > MaxY) {
            y = MaxY;
        }
        if (y < 0) {
            y = 0;
        }
        super.scrollTo(x, y);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
            mScrollingChildHelper.setNestedScrollingEnabled(true);
        }
        return mScrollingChildHelper;
    }

//以下为接口实现--------------------------------------------------

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }
}
