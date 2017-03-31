package com.highway.study.customedittext;

import android.os.SystemClock;

/**
 * 简单计算view滑动距离的工具类
 * 系统方法
 * {@link android.widget.Scroller}
 * @author JH
 * @date 2017/3/31
 */
public class MyScroller {
    private static final String TAG = "MyScroller";
    /**
     * X轴的起始坐标
     */
    private float startY;
    /**
     * Y轴的起始坐标
     */
    private float startX;

    /**
     * 在X轴要移动的距离
     */
    private int distanceX;
    /**
     * 在Y轴要移动的距离
     */
    private int distanceY;
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
    private long totalTime = 250;
    /**
     * 当前滑动的位置
     */
    private float currX;

    public float getCurrX() {
        return currX;
    }

    public void startScroll(int scrollX, int scrollY, int distanceX, int distanceY) {
        this.startX = scrollX;
        this.startY = scrollY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
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
            float distanceSmart = passtime * distanceX / totalTime;
            currX = distanceSmart + startX;
        } else {
            isFinish = true;
            currX = distanceX + startX;
        }
        return true;
    }

}
