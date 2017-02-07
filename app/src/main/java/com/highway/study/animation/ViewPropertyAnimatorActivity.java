package com.highway.study.animation;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;

import com.highway.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ViewPropertyAnimator
 * 属性动画给我们提供了ValueAnimator类和ObjectAnimator类，在正常情况下，基本都能满足我们对动画操作的需求，
 * 但ValueAnimator类和ObjectAnimator类本身并不是针对View对象的而设计的，而我们在大多数情况下主要都还是对View进行动画操作的，
 * 因此Google官方在Android 3.1系统中补充了ViewPropertyAnimator类，这个类便是专门为View动画而设计的。
 * 当然这个类不仅仅是为提供View而简单设计的，它存在以下优点：
 * 1、专门针对View对象动画而操作的类。
 * 2、提供了更简洁的链式调用设置多个属性动画，这些动画可以同时进行的。
 * 3、拥有更好的性能，多个属性动画是一次同时变化，只执行一次UI刷新（也就是只调用一次invalidate,而n个ObjectAnimator就会进行n次属性变化，就有n次invalidate）。
 * 4、每个属性提供两种类型方法设置。
 * 5、该类只能通过View的animate()获取其实例对象的引用
 */
public class ViewPropertyAnimatorActivity extends AppCompatActivity {

    @Bind(R.id.fab_smile)
    FloatingActionButton mSmileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property_animator);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.text_start_animation)
    public void onAnimateTextClicked() {
        buildAndStartAnimation(mSmileButton);
    }

    @OnClick(R.id.text_reset_animations)
    public void onResetAnimationsClicked() {
        mSmileButton.setAlpha(1f);
        mSmileButton.setScaleY(1f);
        mSmileButton.setScaleX(1f);
    }

    private void buildAndStartAnimation(View view) {
        ViewPropertyAnimator propertyAnimator = view.animate();
        propertyAnimator.alpha(0);
        propertyAnimator.scaleX(0).scaleY(0);
        propertyAnimator.setDuration(500l);
        propertyAnimator.setInterpolator(new LinearInterpolator());
        propertyAnimator.start();
    }
}
