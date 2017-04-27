package com.highway.study.coordinatorLayout;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.highway.study.R;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        initViews();
        initDatas();
        initEvents();
    }

    private static final String TAG = "MainActivity";
    private String[] mTitles = new String[]{"简介", "评价", "相关"};
    private SimpleViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private TabFragment[] mFragments = new TabFragment[mTitles.length];
    private ImageView textView;
    private ImageView textView1;
    private ImageView title;
    private ImageView title1;

    private TextView bifen1;
    private TextView bifen;
    private TextView bifen2;
    private TextView bifen3;

    private AppBarLayout mAppBar;

    private boolean isFirst;

    FrameLayout zhudui;
    FrameLayout kedui;
    FrameLayout bifenlayout;
    RelativeLayout bifen1layout;


    private void initViews() {
        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        textView = (ImageView) findViewById(R.id.textView);
        textView1 = (ImageView) findViewById(R.id.textView1);
        title = (ImageView) findViewById(R.id.title);
        title1 = (ImageView) findViewById(R.id.title1);
        bifen1 = (TextView) findViewById(R.id.bifen1);
        bifen2 = (TextView) findViewById(R.id.bifen2);
        bifen3 = (TextView) findViewById(R.id.bifen3);
        bifen = (TextView) findViewById(R.id.bifen);
        mAppBar = (AppBarLayout) findViewById(R.id.appbar);
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                scrollY(verticalOffset, appBarLayout.getTotalScrollRange());
                Log.e(TAG, "onOffsetChanged: " + verticalOffset );
            }
        });
        zhudui = (FrameLayout) findViewById(R.id.zhudui);
        kedui = (FrameLayout) findViewById(R.id.kedui);
        bifenlayout = (FrameLayout) findViewById(R.id.bifenlayout);
        bifen1layout = (RelativeLayout) findViewById(R.id.bifen1layout);


    }

    private void initDatas() {
        mIndicator.setTitles(mTitles);

        for (int i = 0; i < mTitles.length; i++) {
            mFragments[i] = TabFragment.newInstance(mTitles[i]);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    private void initEvents() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    float desX;
    float desY;
    float scaleX;
    float scaleY;
    float imageX;
    float imageY;

    float desX1;
    float desY1;
    float scaleX1;
    float scaleY1;
    float imageX1;
    float imageY1;

    float desX2;
    float desY2;
    float textSize;
    float diSize;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Log.e(TAG, "onGlobalLayout ");
//            }
//        });

        int[] location = new int[2];
        int[] location2 = new int[2];
        textView.getLocationOnScreen(location);
        zhudui.getLocationOnScreen(location2);
        scaleX = getDistance(0, textView, title);
        scaleY = getDistance(1, textView, title);
        imageX = textView.getWidth();
        imageY = textView.getHeight();
        desX = location2[0] - location[0];
        desY = location2[1] - location[1];




        int[] location1 = new int[2];
        int[] location4 = new int[2];
        textView1.getLocationOnScreen(location1);
        kedui.getLocationOnScreen(location4);
        scaleX1 = getDistance(0, textView1, title1);
        scaleY1 = getDistance(1, textView1, title1);
        imageX1 = textView1.getWidth();
        imageY1 = textView1.getHeight();
        desX1 = location4[0] - location1[0];
        desY1 = location4[1] - location1[1];
//                Log.e(TAG, "desx = " + desX1 + " desY = " + desY1);
//                Log.e(TAG, "scalex = " + imageX1);
//                Log.e(TAG, "scaley = " + imageY1);

        int[] location3 = new int[2];
        int[] location6 = new int[2];
        bifen1layout.getLocationOnScreen(location3);
        bifenlayout.getLocationOnScreen(location6);
//        scaleX2 = getDistance(0, bifen1, bifen);
//        scaleY2 = getDistance(1, bifen1, bifen);
//        imageX2 = bifen1.getWidth();
//        imageY2 = bifen1.getHeight();
        desX2 = location6[0] - location3[0];
        desY2 = location6[1] - location3[1];
        textSize = bifen1.getTextSize();
        diSize = textSize - bifen.getTextSize();

        Log.e(TAG, "textSize = " + textSize + "................");

    }

    //    25
    public float getDpTopx(int value) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    public float getDistance(int scale, View view, View view1) {
        float result = 0;
        if (scale == 0) {
            result = view.getWidth() - view1.getWidth();
        } else {
            result = view.getHeight() - view1.getWidth();
        }
        return result;
    }

    public void scrollY(int scrolly, int topHight) {
        if (isFirst) {
//            Log.e(TAG, "scrolly = " + scrolly);
//            Log.e(TAG, "topHight = " + topHight);
            float pre = Math.abs(scrolly) * 1.0f / topHight;
            textView.setTranslationY(desY * pre);
            textView.setTranslationX(desX * pre);
            textView.setScaleX((imageX - pre * scaleX) / imageX);
            textView.setScaleY((imageY - pre * scaleY) / imageY);

            textView1.setTranslationY(desY1 * pre);
            textView1.setTranslationX(desX1 * pre);
            textView1.setScaleX((imageX1 - pre * scaleX1) / imageX1);
            textView1.setScaleY((imageY1 - pre * scaleY1) / imageY1);

            bifen1layout.setTranslationY(desY2 * pre);
            bifen1layout.setTranslationX(desX2 * pre);
//        bifen1.setTextSize(textSize - pre * diSize);
//            if (pre <= 0.3) {

//                float b = (float) (Math.round(pre * 100000000)) / 100000000;
//                Log.e(TAG, "b = " + b + "................");
//                bifen1.getPaint().setTextSize(textSize - 2 * b * diSize);
//                bifen1.postInvalidate();
            float size = lerp(textSize, textSize - diSize, pre);
            Log.e(TAG, "lerp: " + size);
                bifen1.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
//                bifen1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 2 * Math.abs(scrolly) * diSize / topHight);
                bifen2.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                bifen3.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
//            bifen2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 2 * Math.abs(scrolly) * diSize / topHight);
//                bifen3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 2 * Math.abs(scrolly) * diSize / topHight);
//                Log.e(TAG, "SIZE = " + (textSize - 2 * b * diSize));
//            }
//        bifen1.setTextScaleY((imageY2 - pre * scaleY2) / imageY2);
//        bifen1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - pre * diSize);
//        bifen1.setText("100:100");
        } else {
            isFirst = true;
        }

    }


    private static float lerp(float startValue, float endValue, float fraction) {
//        if (interpolator != null) {
            fraction = LINEAR_INTERPOLATOR.getInterpolation(fraction);
//        }
        return startValue + (fraction * (endValue - startValue));
    }

    static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new FastOutLinearInInterpolator();
    static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();
    static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

}
