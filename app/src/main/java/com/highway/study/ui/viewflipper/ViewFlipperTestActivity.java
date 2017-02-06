package com.highway.study.ui.viewflipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.highway.study.R;

import java.util.ArrayList;
import java.util.List;

/**
 * viewflipper 实现滚动头条
 *
 * <p>
 * android:autoStart：设置自动加载下一个View
 * android:flipInterval：设置View之间切换的时间间隔
 * android:inAnimation：设置切换View的进入动画
 * android:outAnimation：设置切换View的退出动画
 * <p>
 * isFlipping： 判断View切换是否正在进行
 * setFilpInterval：设置View之间切换的时间间隔
 * startFlipping：开始View的切换，而且会循环进行
 * stopFlipping：停止View的切换
 * setOutAnimation：设置切换View的退出动画
 * setInAnimation：设置切换View的进入动画
 * showNext： 显示ViewFlipper里的下一个View
 * showPrevious：显示ViewFlipper里的上一个View
 */
public class ViewFlipperTestActivity extends AppCompatActivity {

    ViewFlipper flipper;
    List<BannerBean> bannerBeen = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper_test);
        flipper = (ViewFlipper) findViewById(R.id.viewflipper);
        updateBanner();
    }

    private void updateBanner() {
        BannerBean bean = new BannerBean();
        bean.setContent("广告111111");
        bannerBeen.add(bean);
        BannerBean bean1 = new BannerBean();
        bean1.setContent("广告22222");
        bannerBeen.add(bean1);
        BannerBean bean2 = new BannerBean();
        bean2.setContent("广告33333");
        bannerBeen.add(bean2);
        BannerBean bean3 = new BannerBean();
        bean3.setContent("广告44444");
        bannerBeen.add(bean3);

        for (int i = 0; i < bannerBeen.size(); i++) {
            final int pos = i;
            View view = LayoutInflater.from(this).inflate(R.layout.viewflipper_item, null);
            TextView title = (TextView) view.findViewById(R.id.tv_title);
            BannerBean bannerBean = bannerBeen.get(i);
            title.setText(bannerBean.getContent());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewFlipperTestActivity.this, "positon" + pos, Toast.LENGTH_LONG).show();
                }
            });
            flipper.addView(view);
        }

        flipper.startFlipping();
    }

}
