package com.dailystudy.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author JH
 * @date 2017/4/10
 */


public class FatherView extends LinearLayout {

    private static final String TAG = "FatherView";
    public FatherView(Context context) {
        super(context);
    }

    public FatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "FatherView onTouchEvent " + TouchEventUtil.getTouchAction(event.getAction()));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "FatherView dispatchTouchEvent " + TouchEventUtil.getTouchAction(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.e(TAG, "FatherView onInterceptTouchEvent " + TouchEventUtil.getTouchAction(event.getAction()));
        return super.onInterceptTouchEvent(event);
    }
}
