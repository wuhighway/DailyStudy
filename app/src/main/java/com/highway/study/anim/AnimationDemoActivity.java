package com.highway.study.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.highway.study.R;
import com.highway.study.util.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationDemoActivity extends AppCompatActivity {

    private static final String TAG = "AnimationDemoActivity";
    @Bind(R.id.animation_button)
    Button start;
    @Bind(R.id.text)
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.animation_button)
    public void click(View v) {
        if (v.getId() == R.id.animation_button) {
//            startAlpha();
//            startRotate();
//            startTranslate();
//            startScale();
//            animationSet();
//            startAnimator();
//            propertyValuesHolder();
//            Viewanimate();
//            valueAnimator();
//            keyFrame();
//            keyframeKing();
            animatorSet();

        }

    }

    /**
     * alpha
     */
    private void startAlpha() {
        AlphaAnimation aa = new AlphaAnimation(1, 0);
        aa.setFillAfter(false); // 设置动画结束后是否停留在当前位置
        aa.setDuration(1000);
        content.startAnimation(aa);
    }

    /**
     * rotate
     */
    private void startRotate() {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        rotate.setDuration(1000);
//        rotate.setRepeatMode(Animation.REVERSE);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);
        content.startAnimation(rotate);
    }

    /**
     * translate
     */
    private void startTranslate() {
        TranslateAnimation translate = new TranslateAnimation(0, 1000, 0, 1000);
        translate.setDuration(1000);
        content.startAnimation(translate);
    }

    /**
     * scale
     */
    private void startScale() {
        ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        content.startAnimation(scale);
    }

    /**
     * animationSet
     */
    private void animationSet() {
        AnimationSet animationSet = new AnimationSet(true);
//        TranslateAnimation translate = new TranslateAnimation(0, 1000, 0, 1000);
//        translate.setDuration(1000);
        ScaleAnimation scale = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(rotate);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        content.startAnimation(animationSet);
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Logger.e(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Logger.e(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Logger.e(TAG, "onAnimationRepeat");
            }
        });
    }

    /**
     * animator
     */
    private void startAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(content, "translationX", 0, 300);
        animator.setDuration(1000);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    /**
     *
     */
    private void propertyValuesHolder() {
        PropertyValuesHolder holder = PropertyValuesHolder.ofFloat("translationX", 0, 300);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(content, holder, holder1, holder2).setDuration(1000).start();
    }


    private void xmlAnimator() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.demo_animotor);
        animator.setTarget(content);
        animator.start();
    }

    /**
     * view 的 animate
     */
    private void Viewanimate() {
        content.animate().alpha(0)
                .setDuration(1000)
                .scaleX(0f)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e(TAG, "startAnima");
                    }
                })
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e(TAG, "endAnima");
                    }
                }).start();
    }

    private void shrank() {
//        AnimationDrawable d = (AnimationDrawable) ContextCompat.getDrawable(this, R.drawable.shrank_file);
//        holder.setImageDrawable(d);
//        d.start();
        start.setBackgroundResource(R.drawable.shrank_file);
        AnimationDrawable drawable = (AnimationDrawable) start.getBackground();
        drawable.start();
    }

    private void valueAnimator() {
        ValueAnimator animator = ValueAnimator.ofObject(new CharEvaluator(), new Character('a'), new Character('z'));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char text = (char) animation.getAnimatedValue();
                content.setText(String.valueOf(text));
            }
        });
        animator.setDuration(5000);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    /**
     * keyframe
     */
    private void keyFrame() {
        Keyframe keyframe0 = Keyframe.ofFloat(0f, 0);
        Keyframe keyframe1 = Keyframe.ofFloat(0.1f, 20f);
        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, -20f);
        Keyframe keyframe3 = Keyframe.ofFloat(0.3f, 20f);
        Keyframe keyframe4 = Keyframe.ofFloat(0.4f, -20f);
        Keyframe keyframe5 = Keyframe.ofFloat(0.5f, 20f);
        Keyframe keyframe6 = Keyframe.ofFloat(0.6f, -20f);
        Keyframe keyframe7 = Keyframe.ofFloat(0.7f, 20f);
        Keyframe keyframe8 = Keyframe.ofFloat(0.8f, -20f);
        Keyframe keyframe9 = Keyframe.ofFloat(0.9f, 20f);
        Keyframe keyframe10 = Keyframe.ofFloat(1f, 0);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("rotation",
                keyframe0, keyframe1, keyframe2, keyframe3, keyframe4, keyframe5,
                keyframe6, keyframe7, keyframe8, keyframe9, keyframe10);
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(content, holder);
        animator.setDuration(2000);
        animator.start();
    }

    private int count = 0;

    /**
     * ring
     */
    private void keyframeKing() {
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder1 = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);

        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder2 = PropertyValuesHolder.ofKeyframe("ScaleX", scaleXframe0, scaleXframe1, scaleXframe2, scaleXframe3, scaleXframe4, scaleXframe5, scaleXframe6, scaleXframe7, scaleXframe8, scaleXframe9, scaleXframe10);

        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleYframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleYframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleYframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder3 = PropertyValuesHolder.ofKeyframe("ScaleY", scaleYframe0, scaleYframe1, scaleYframe2, scaleYframe3, scaleYframe4, scaleYframe5, scaleYframe6, scaleYframe7, scaleYframe8, scaleYframe9, scaleYframe10);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(content, frameHolder1, frameHolder2, frameHolder3);
        animator.setDuration(1000);
        animator.start();
        animator.setRepeatCount(5);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                count++;
                Logger.e(TAG, "加载动画数量 = " + count);
            }
        });
    }


    private void animatorSet() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(content, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(content, "translationY", 0, 300, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(content, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.playTogether(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }


}
