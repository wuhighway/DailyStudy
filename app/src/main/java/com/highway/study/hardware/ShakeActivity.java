package com.highway.study.hardware;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.highway.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShakeActivity extends AppCompatActivity {
    /**
     * 摇一摇listener.
     */
    private ShakeListener mShakeListener;
    /**
     * 震动.
     */
    private Vibrator mVirate;

    @Bind(R.id.tv_shake)
    TextView mTvView;

    private int tv = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        ButterKnife.bind(this);
        mShakeListener = new ShakeListener(this);
        mVirate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerShakeListener();
    }

    /**
     * 注册摇一摇监听
     */
    private void registerShakeListener() {
        mShakeListener.start();
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                mVirate.vibrate(300);
                mTvView.setText("第" + tv + "次");
                tv++;
            }
        });
    }

}
