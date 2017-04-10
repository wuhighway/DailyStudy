package com.dailystudy.touchevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "Activity onTouchEvent " + TouchEventUtil.getTouchAction(event.getAction()));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "Activity dispatchTouchEvent " + TouchEventUtil.getTouchAction(event.getAction()));
        return super.dispatchTouchEvent(event);
    }
}
