package com.dailystudy.touchevent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author JH
 * @date 2017/4/11
 */


public class TouchLinearLayout extends LinearLayout {

    public TouchLinearLayout(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
    }

    public TouchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
    }

    public TouchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int width = getWidth() / getChildCount();
        int height = getHeight();
        int count = getChildCount();
        float eventX = event.getX();
        if (eventX < width) {
            getChildAt(0).dispatchTouchEvent(event);
        } else if (eventX > width && eventX < 2 * width) {
            float eventY = event.getY();
            if (eventY < height / 2) {
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    child.dispatchTouchEvent(event);
                }
            } else {
                getChildAt(1).dispatchTouchEvent(event);
            }
        } else if (eventX > 2 * width) {
            getChildAt(2).dispatchTouchEvent(event);
        }

        return true;
    }
}
