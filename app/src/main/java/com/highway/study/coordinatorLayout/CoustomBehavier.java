package com.highway.study.coordinatorLayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.highway.study.R;

/**
 * @author JH
 * @date 2017/5/2
 */


public class CoustomBehavier extends CoordinatorLayout.Behavior<FrameLayout> {
    private static final String TAG = "CoustomBehavier";
    private boolean isFirst = true;

    private ImageView textView;
    private ImageView textView1;
    private ImageView title;
    private ImageView title1;

    private TextView bifen1;
    private TextView bifen;
    private TextView bifen2;
    private TextView bifen3;

    FrameLayout zhudui;
    FrameLayout kedui;
    FrameLayout bifenlayout;
    RelativeLayout bifen1layout;
    RelativeLayout toolbarTitle;

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


    public CoustomBehavier(Context context, AttributeSet attrs) {
        Log.e(TAG, "INIT");
    }

    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params) {
        super.onAttachedToLayoutParams(params);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        boolean isChild = child.getId() == R.id.layout_header;
//        Log.e(TAG, "CoustomBehavier");
        return dependency instanceof AppBarLayout && isChild;
    }

    private void initView(View parent) {
        textView = (ImageView) parent.findViewById(R.id.textView);
        textView1 = (ImageView) parent.findViewById(R.id.textView1);
        title = (ImageView) parent.findViewById(R.id.title);
        title1 = (ImageView) parent.findViewById(R.id.title1);
        bifen1 = (TextView) parent.findViewById(R.id.bifen1);
        bifen2 = (TextView) parent.findViewById(R.id.bifen2);
        bifen3 = (TextView) parent.findViewById(R.id.bifen3);
        bifen = (TextView) parent.findViewById(R.id.bifen);
        zhudui = (FrameLayout) parent.findViewById(R.id.zhudui);
        kedui = (FrameLayout) parent.findViewById(R.id.kedui);
        bifenlayout = (FrameLayout) parent.findViewById(R.id.bifenlayout);
        bifen1layout = (RelativeLayout) parent.findViewById(R.id.bifen1layout);
        toolbarTitle = (RelativeLayout) parent.findViewById(R.id.toolbar_title);
    }

    private void initPosition() {
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

        int[] location3 = new int[2];
        int[] location6 = new int[2];
        bifen1layout.getLocationOnScreen(location3);
        bifenlayout.getLocationOnScreen(location6);
        desX2 = location6[0] - location3[0];
        desY2 = location6[1] - location3[1];
        textSize = bifen1.getTextSize();
        diSize = textSize - bifen.getTextSize();
        Log.e(TAG, "textsize = " + textSize);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {
        AppBarLayout barLayout = (AppBarLayout) dependency;
        if (isFirst) {
            initView(parent);
            initPosition();
            isFirst = false;
            barLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    scrollY(verticalOffset, appBarLayout.getTotalScrollRange());
                }
            });
        }
        return true;
    }

    public void scrollY(int scrolly, int topHight) {
        float pre = Math.abs(scrolly) * 1.0f / topHight;
        Log.e(TAG, "pre = " + pre);
        if (pre == 1) {
            toolbarTitle.setVisibility(View.VISIBLE);
            bifen1layout.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
        } else {
            toolbarTitle.setVisibility(View.GONE);
            bifen1layout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
        }
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
        if (pre <= 0.5) {
            bifen1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 2*Math.abs(scrolly) * diSize / topHight);
            bifen2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 2*Math.abs(scrolly) * diSize / topHight);
            bifen3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 2*Math.abs(scrolly) * diSize / topHight);
            bifen.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize - 2*Math.abs(scrolly) * diSize / topHight);
            if (pre == 0.5) {
                Log.e(TAG, "textsize = 0.5 " + (textSize - 2*Math.abs(scrolly) * diSize / topHight));
            }
        }
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

}
