package com.highway.study.customedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.highway.study.R;

public class SecurityActivity extends AppCompatActivity {

    CoustomViewPager pager;

    private int[] ids = {R.mipmap.a1, R.mipmap.a2, R.mipmap.a3, R.mipmap.a4, R.mipmap.a5, R.mipmap.a6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        pager = (CoustomViewPager) findViewById(R.id.vp);
        //添加6页面
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            //添加到MyViewPager这个View中
            pager.addView(imageView);
        }

        //添加测试页面
        View testview = View.inflate(this, R.layout.text, null);
        pager.addView(testview, 2);

    }
}
