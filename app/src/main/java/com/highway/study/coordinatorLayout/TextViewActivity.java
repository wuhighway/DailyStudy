package com.highway.study.coordinatorLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.highway.study.MainActivity;
import com.highway.study.R;
import com.highway.study.coordinatorLayout.view.MathchProgressView;

public class TextViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_select);
//        initViews();
//        initDatas();
//        initEvents();
        initView();
    }

    private static final String TAG = "TextViewActivity";
    private boolean isFirst;
    private boolean isInit;


    public void scrollY(int scrolly, int topHight) {
        if (isFirst) {
            float pre = Math.abs(scrolly) * 1.0f / topHight;
            Log.e(TAG, "pre = " + pre);
            if (pre == 1.0f) {
//                status_match.setVisibility(View.VISIBLE);
                status_header_back.setVisibility(View.VISIBLE);
            } else {
                status_header_back.setVisibility(View.GONE);
                status_match.setVisibility(View.GONE);
            }
            bifen_guest.setTranslationX(bifen_guest_X * pre);
            bifen_guest.setTranslationY(bifen_guest_Y * pre);

            bifen_host.setTranslationX(bifen_host_X * pre);
            bifen_host.setTranslationY(bifen_host_Y * pre);

            guest_name.setTranslationX(guest_name_X * pre);
            guest_name.setTranslationY(guest_name_Y * pre);

            host_name.setTranslationX(host_name_X * pre);
            host_name.setTranslationY(host_name_Y * pre);

            Log.e(TAG, "bifen_guest_X = " + bifen_guest_X * pre);
            float alpha = 1 - pre * 10;
            if (alpha < 0) {
                alpha = 0;
            }
            match_time.setAlpha(alpha);
            match_data.setAlpha(alpha);
            tv_kedui.setAlpha(alpha);
            tv_zhudui.setAlpha(alpha);
            zhibo.setAlpha(alpha);
            image_guest.setAlpha(alpha);
            image_host.setAlpha(alpha);

            bifen_guest.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - pre * textSizeDis);
            bifen_host.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - pre * textSizeDis);

        } else {
            isFirst = true;
        }
    }


    /**
     * 显示比分
     */
    private TextView bifen_guest;
    private TextView bifen_host;
    private TextView guest_name;
    private TextView host_name;

    private TextView status_header_back;
    private TextView match_time;
    private TextView match_data;
    private TextView tv_kedui;
    private TextView tv_zhudui;
    private TextView status_match;
    private ImageView zhibo;
    private ImageView image_guest;
    private ImageView image_host;


    /**
     * 隐藏比分
     */
    private TextView bifen_guest_back;
    private TextView bifen_host_back;
    private TextView guest_name_back;
    private TextView host_name_back;

    private AppBarLayout mAppBar;

    /**
     * 位移的距离字体大小的变化
     */
    private float textSize;

    private float textSizeDis;

    private float bifen_guest_X;
    private float bifen_guest_Y;

    private float bifen_host_X;
    private float bifen_host_Y;

    private float guest_name_X;
    private float guest_name_Y;

    private float host_name_X;
    private float host_name_Y;

    private void initView() {
        bifen_guest = (TextView) findViewById(R.id.bifen_guest);
        bifen_guest_back = (TextView) findViewById(R.id.bifen_guest_back);
        bifen_host = (TextView) findViewById(R.id.bifen_host);
        bifen_host_back = (TextView) findViewById(R.id.bifen_host_back);
        guest_name = (TextView) findViewById(R.id.guest_name);
        guest_name_back = (TextView) findViewById(R.id.guest_name_back);
        host_name = (TextView) findViewById(R.id.host_name);
        host_name_back = (TextView) findViewById(R.id.host_name_back);
        status_header_back = (TextView) findViewById(R.id.status_header_back);
        match_time = (TextView) findViewById(R.id.match_time);
        match_data = (TextView) findViewById(R.id.match_data);
        tv_kedui = (TextView) findViewById(R.id.tv_kedui);
        tv_zhudui = (TextView) findViewById(R.id.tv_zhudui);
        status_match = (TextView) findViewById(R.id.status_match);
        zhibo = (ImageView) findViewById(R.id.zhibo);
        image_guest = (ImageView) findViewById(R.id.image_guest);
        image_host = (ImageView) findViewById(R.id.image_host);
        mAppBar = (AppBarLayout) findViewById(R.id.appbar);
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                scrollY(verticalOffset, appBarLayout.getTotalScrollRange());
            }
        });

        MathchProgressView = (MathchProgressView) findViewById(R.id.MathchProgressView);
        MathchProgressView.setProgress(0);
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    MathchProgressView MathchProgressView;
    int progress = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progress == 100) {
                progress = 0;
            }
            progress += 2;
            handler.sendEmptyMessageDelayed(0, 1000);
            MathchProgressView.setProgress(progress);

        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_UP) {
            float per = Math.abs(mAppBar.getY()) / mAppBar.getTotalScrollRange();
            boolean setExpanded = (per <= 0.5F);
            mAppBar.setExpanded(setExpanded, true);
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!isInit) {
            /** 主队比分*/
            int[] bifen_guest_location = new int[2];
            int[] bifen_guest_back_location = new int[2];
            bifen_guest.getLocationOnScreen(bifen_guest_location);
            bifen_guest_back.getLocationOnScreen(bifen_guest_back_location);
            textSize = bifen_guest.getTextSize();
            textSizeDis = textSize - bifen_guest_back.getTextSize();
            bifen_guest_X = bifen_guest_back_location[0] - bifen_guest_location[0];
            bifen_guest_Y = bifen_guest_back_location[1] - bifen_guest_location[1];
            /** 客队*/
            int[] bifen_host_location = new int[2];
            int[] bifen_host_back_location = new int[2];
            bifen_host.getLocationOnScreen(bifen_host_location);
            bifen_host_back.getLocationOnScreen(bifen_host_back_location);
            bifen_host_X = bifen_host_back_location[0] - bifen_host_location[0];
            bifen_host_Y = bifen_host_back_location[1] - bifen_host_location[1];

            /** 主队*/

            int[] guest_name_location = new int[2];
            int[] guest_name_back_location = new int[2];
            guest_name.getLocationOnScreen(guest_name_location);
            guest_name_back.getLocationOnScreen(guest_name_back_location);
            guest_name_X = guest_name_back_location[0] - guest_name_location[0];
            guest_name_Y = guest_name_back_location[1] - guest_name_location[1];

            /** 客队*/
            int[] host_name_location = new int[2];
            int[] host_name_back_location = new int[2];
            host_name.getLocationOnScreen(host_name_location);
            host_name_back.getLocationOnScreen(host_name_back_location);
            host_name_X = host_name_back_location[0] - host_name_location[0];
            host_name_Y = host_name_back_location[1] - host_name_location[1];
            isInit = true;
        }

    }

//    public void scrollY(int scrolly, int topHight) {
//        if (isFirst) {
//    }
}
