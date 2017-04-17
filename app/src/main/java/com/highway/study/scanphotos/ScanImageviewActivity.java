package com.highway.study.scanphotos;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.highway.study.R;
import com.highway.study.scanphotos.animations.DepthPageTransformer;
import com.highway.study.widget.HackyViewPager;

import java.util.ArrayList;

/**
 * 浏览图片，photoview
 */
public class ScanImageviewActivity extends AppCompatActivity {
    private HackyViewPager mViewPager;
    ImagePagerAdapter adapter;
    ArrayList<Integer> integers = new ArrayList<>();
    private Toolbar toolbar;
    private RelativeLayout ActivityBackground;


    private boolean fullScreenMode, customUri = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_imageview);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.bringToFront();
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        setupSystemUI();
//        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener
//                (new View.OnSystemUiVisibilityChangeListener() {
//                    @Override
//                    public void onSystemUiVisibilityChange(int visibility) {
//                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) showSystemUI();
//                        else hideSystemUI();
//                    }
//                });
        initData();
        mViewPager = (HackyViewPager) findViewById(R.id.photos_pager);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        adapter = new ImagePagerAdapter(getSupportFragmentManager(), integers);
        mViewPager.setAdapter(adapter);

        Display aa = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        if (aa.getRotation() == Surface.ROTATION_90) {
            Configuration configuration = new Configuration();
            configuration.orientation = Configuration.ORIENTATION_LANDSCAPE;
            onConfigurationChanged(configuration);
        }
    }


//    private void setupUI() {
//
//        /**** Theme ****/
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(
//                isApplyThemeOnImgAct()
//                        ? ColorPalette.getTransparentColor (getPrimaryColor(), getTransparency())
//                        : ColorPalette.getTransparentColor(getDefaultThemeToolbarColor3th(), 175));
//
//        toolbar.setPopupTheme(getPopupToolbarStyle());
//
//        ActivityBackground = (RelativeLayout) findViewById(R.id.PhotoPager_Layout);
//        ActivityBackground.setBackgroundColor(getBackgroundColor());
//
//        setStatusBarColor();
//        setNavBarColor();
//
//        securityObj.updateSecuritySetting();
//
//        /**** SETTINGS ****/
//
//        if (SP.getBoolean("set_max_luminosity", false))
//            updateBrightness(1.0F);
//        else try {
//            float brightness = android.provider.Settings.System.getInt(
//                    getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
//            brightness = brightness == 1.0F ? 255.0F : brightness;
//            updateBrightness(brightness);
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        if (SP.getBoolean("set_picture_orientation", false))
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//        else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
//            params.setMargins(0, 0, Measure.getNavigationBarSize(ScanImageviewActivity.this).x, 0);
//        else
//            params.setMargins(0, 0, 0, 0);
//
//        toolbar.setLayoutParams(params);
//    }

    private void initData() {
        integers.add(R.drawable.intro_1);
        integers.add(R.drawable.intro_2);
        integers.add(R.drawable.intro_4);
        integers.add(R.drawable.intro_5);
    }

    private void setupSystemUI() {
        toolbar.animate().translationY(Measure.getStatusBarHeight(getResources())).setInterpolator(new DecelerateInterpolator())
                .setDuration(0).start();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void showSystemUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                toolbar.animate().translationY(Measure.getStatusBarHeight(getResources())).setInterpolator(new DecelerateInterpolator())
                        .setDuration(240).start();
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                fullScreenMode = false;
                changeBackGroundColor();
            }
        });
    }

    private void hideSystemUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator())
                        .setDuration(200).start();
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);

                fullScreenMode = true;
                changeBackGroundColor();
            }
        });
    }

    private void changeBackGroundColor() {
        int colorTo;
        int colorFrom;
        if (fullScreenMode) {
            colorFrom = getResources().getColor(R.color.bg);
            colorTo = (ContextCompat.getColor(this, R.color.bg1));
        } else {
            colorFrom = (ContextCompat.getColor(this, R.color.bg1));
            colorTo = getResources().getColor(R.color.bg);
        }
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(240);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                ActivityBackground.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    public void toggleSystemUI() {
        if (fullScreenMode)
            showSystemUI();
        else hideSystemUI();
    }

}
