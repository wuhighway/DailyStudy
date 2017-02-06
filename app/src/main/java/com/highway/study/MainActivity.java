package com.highway.study;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.highway.study.coustomview.loadingview.CoustomActivity;
import com.highway.study.takephoto.TakePhotoActivity;
import com.highway.study.ui.viewflipper.ViewFlipperTestActivity;
import com.highway.study.ui.viewflipper.datepicker.DatePickerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.viewflipper).setOnClickListener(this);
        findViewById(R.id.takephoto).setOnClickListener(this);
        findViewById(R.id.datepick).setOnClickListener(this);
        findViewById(R.id.coustom).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewflipper:
                startActivity(new Intent(this, ViewFlipperTestActivity.class));
                break;
            case R.id.takephoto:
                startActivity(new Intent(this, TakePhotoActivity.class));
                break;
            case R.id.datepick:
                startActivity(new Intent(this, DatePickerActivity.class));
                break;
            case R.id.coustom:
                startActivity(new Intent(this, CoustomActivity.class));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            //  真正的沉浸式体验，适用于SDK>=19，可以拉出导航栏
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );

            //   实现全屏，去掉系统标题栏，适合于游戏、电影等沉浸式体验
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


            //把系统时间放出来
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                getWindow().setStatusBarColor(Color.TRANSPARENT);
//            }
            //另外一种方式
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().getDecorView().setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                );
//                getWindow().setStatusBarColor(Color.TRANSPARENT);
//                getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            }
        }
    }
}
