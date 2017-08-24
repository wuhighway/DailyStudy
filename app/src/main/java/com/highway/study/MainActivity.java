package com.highway.study;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.highway.study.Retrofit.RetrofitActivity;
import com.highway.study.androidbase.BaseActivity;
import com.highway.study.anim.AnimActivity;
import com.highway.study.anim.DividerItemDecoration;
import com.highway.study.coordinatorLayout.TextViewActivity;
import com.highway.study.coustomview.loadingview.CoustomActivity;
import com.highway.study.customedittext.SecurityActivity;
import com.highway.study.cutdowntimer.CountdownTimerActivity;
import com.highway.study.getui.GetuiSdkDemoActivity;
import com.highway.study.hardware.ShakeActivity;
import com.highway.study.javaandh5.JavaAndH5Activity;
import com.highway.study.keyboard.KeyBoardActivity;
import com.highway.study.recyclerview.RecyclerViewActivity;
import com.highway.study.remoteview.RemoteViewActivity;
import com.highway.study.rxjava.RxJavaActivity;
import com.highway.study.scanphotos.ScanImageviewActivity;
import com.highway.study.service.ServiceActivity;
import com.highway.study.shareelem.ShareTractionActivity;
import com.highway.study.spannablestring.SpannableStringActivity;
import com.highway.study.sqlite.SQliteTestActivity;
import com.highway.study.takephoto.TakePhotoActivity;
import com.highway.study.ui.viewflipper.ViewFlipperTestActivity;
import com.highway.study.ui.viewflipper.datepicker.DatePickerActivity;
import com.highway.study.window.TestWindowActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] apiArray = getResources().getStringArray(R.array.title);
        TitleAdapter titleAdapter = new TitleAdapter(apiArray, onRecyclerItemClick);
        recyclerView.setAdapter(titleAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
    }

    private TitleAdapter.OnRecyclerItemClick onRecyclerItemClick =
            new TitleAdapter.OnRecyclerItemClick() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(MainActivity.this, ViewFlipperTestActivity.class);
                            break;
                        case 1:
                            intent = new Intent(MainActivity.this, TakePhotoActivity.class);
                            break;
                        case 2:
                            intent = new Intent(MainActivity.this, DatePickerActivity.class);
                            break;
                        case 3:
                            intent = new Intent(MainActivity.this, CoustomActivity.class);
                            break;
                        case 4:
                            intent = new Intent(MainActivity.this, AnimActivity.class);
                            break;
                        case 5:
                            intent = new Intent(MainActivity.this, ServiceActivity.class);
                            break;
                        case 6:
                            intent = new Intent(MainActivity.this, ShareTractionActivity.class);
                            break;
                        case 7:
                            intent = new Intent(MainActivity.this, SQliteTestActivity.class);
                            break;
                        case 8:
                            intent = new Intent(MainActivity.this, KeyBoardActivity.class);
                            break;
                        case 9:
                            intent = new Intent(MainActivity.this, SpannableStringActivity.class);
                            break;
                        case 10:
                            intent = new Intent(MainActivity.this, RetrofitActivity.class);
                            break;
                        case 11:
                            intent = new Intent(MainActivity.this, ShakeActivity.class);
                            break;
                        case 12:
                            intent = new Intent(MainActivity.this, RxJavaActivity.class);
                            break;
                        case 13:
                            intent = new Intent(MainActivity.this, CountdownTimerActivity.class);
                            break;
                        case 14:
                            intent = new Intent(MainActivity.this, ScanImageviewActivity.class);
                            break;
                        case 15:
                            intent = new Intent(MainActivity.this, SecurityActivity.class);
                            break;
                        case 16:
                            intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                            break;
                        case 17:
                            intent = new Intent(MainActivity.this, JavaAndH5Activity.class);
                            break;
                        case 18:
                            intent = new Intent(MainActivity.this, TextViewActivity.class);
                            break;
                        case 19:
                            intent = new Intent(MainActivity.this, BaseActivity.class);
                            break;
                        case 20:
                            intent = new Intent(MainActivity.this, TestWindowActivity.class);
                            break;
                        case 21:
                            intent = new Intent(MainActivity.this, RemoteViewActivity.class);
                            break;
                        case 22:
                            intent = new Intent(MainActivity.this, GetuiSdkDemoActivity.class);
                            break;
                    }
                    if (intent != null) startActivity(intent);
                }
            };


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            //  真正的沉浸式体验，适用于SDK>=19，可以拉出导航栏
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            );

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
