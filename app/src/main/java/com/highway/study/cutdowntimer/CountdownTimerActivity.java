package com.highway.study.cutdowntimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.highway.study.R;

/**
 * 倒计时，仿全民夺宝开奖倒计时
 */
public class CountdownTimerActivity extends AppCompatActivity {

    TextView tvTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);
        tvTimer = (TextView) findViewById(R.id.tv_timmer);
        new MyCountDownTimer(400000, 100).start();
    }


    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            tvTimer.setText("揭晓中....");
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    tv_status.setVisibility(View.GONE);
//                    ll_result.setVisibility(View.VISIBLE);
//                    tv_awardee.setText(awardee);
//                }
//            }, 5000);
        }
        /**
         * 处理时间倒计时进行页面刷新
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            int ss = 1000;
            int mi = ss * 60;
            long minute = millisUntilFinished/ mi;
            long second = (millisUntilFinished- minute * mi) / ss;
            long milliSecond = millisUntilFinished  - minute * mi - second * ss;
            String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
            String strSecond = second < 10 ? "0" + second : "" + second;//秒
            String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
            strMilliSecond = milliSecond >100 ? strMilliSecond.substring(0,strMilliSecond.length()-1) : "" + strMilliSecond;
            tvTimer.setText(strMinute + " 分 "+strSecond+"秒"+strMilliSecond);
        }
    }
}
