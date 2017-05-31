package com.highway.study.coordinatorLayout;

import android.os.SystemClock;

/**
 * @author JH
 * @date 2017/4/28
 */


public class SizeScroller {

    private static final String TAG = "SizeScroller";
    /**
     * 初始size
     */
    private float startSize;

    /**
     * 变化的size
     */
    private float distanceSize;

    /**
     * 开始的时间
     */
    private long startTime;
    /**
     * 是否移动完成
     * false没有移动完成
     * true:移动结束
     */
    private boolean isFinish;
    /**
     * 总时间
     */
    private long totalTime = 50;
    /**
     * 当前滑动的位置
     */
    private float currSize;

    public float getCurrX() {
        return currSize;
    }

    public void startScroll(float scrollY, float distanceSize) {
        this.startSize = scrollY;
        this.distanceSize = distanceSize;
        this.startTime = SystemClock.uptimeMillis();
        this.isFinish = false;
    }

    /**
     * 速度
     * 求移动一小段的距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     *
     * true:正在移动
     * false:移动结束
     * @return
     */
    public boolean computeScrollOffect() {
        if (isFinish) {
            return false;
        }
        long endTime = SystemClock.uptimeMillis();
        long passtime = endTime - startTime;
        if (passtime < totalTime) {
            float distanceSmart = passtime * distanceSize / totalTime;
            currSize = distanceSmart + startSize;
        } else {
            isFinish = true;
            currSize = distanceSize + startSize;
        }
        return true;
    }
}
