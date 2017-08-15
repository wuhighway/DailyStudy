package com.highway.study.window;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.highway.study.R;

public class TestWindowActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_window);
        initView();
    }



    private Button mCreateWindowButton;

    private Button mFloatingButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;


    private void initView() {
        mCreateWindowButton = (Button) findViewById(R.id.button1);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }


    /**
     * Flags控制window的显示特性
     * FLAG_NOT_FOCUSABLE 不需要获取焦点
     * FLAG_NOT_TOUCH_MODAL 当前window区域以外的单击事件传递给底层的window，当前的单击事件自己出来
     * FLAG_SHOW_WHEN_LOCKED WINDOW可以在锁屏的界面上显示
     *
     * type标示window的类型：应用Window、子window、和系统window
     * 应用window对应着一个activity。
     * 子window不能单独存在附属在特定的父window中
     * 系统window，toast和系统状态栏 TYPE_SYSTEM_OVERLAY、TYPE_SYSTEM_ERROR
     *
     */


    public void onButtonClick(View v) {
        if (v == mCreateWindowButton) {

            if (Build.VERSION.SDK_INT >= 23) {
                if (Settings.canDrawOverlays(this)) {
                    showWindow();
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(intent);
                }
            } else {
                showWindow();
            }
        }
    }

    private void showWindow() {
        mFloatingButton = new Button(this);
        mFloatingButton.setText("button");
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "我是window", Toast.LENGTH_LONG).show();
            }
        });
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT
                ,WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSLUCENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mFloatingButton.setOnTouchListener(this);
        mWindowManager.addView(mFloatingButton, mLayoutParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int x = (int) event.getX();
                int y = (int) event.getY();
                mLayoutParams.x = rawX;
                mLayoutParams.y = rawY;
                mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        try {
//            mWindowManager.removeView(mFloatingButton);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
