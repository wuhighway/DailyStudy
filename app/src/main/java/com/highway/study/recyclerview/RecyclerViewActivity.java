package com.highway.study.recyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.highway.study.R;

/**
 *
 * recyclerView
 * @author JH
 * @date 2017/3/30
 */
public class RecyclerViewActivity extends AppCompatActivity {

    Handler handler = new Handler();
    private TextView tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        tvShow = (TextView) findViewById(R.id.tv_word);
        IndexView indexView = (IndexView) findViewById(R.id.index);
        indexView.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                tvShow.setVisibility(View.VISIBLE);
                tvShow.setText(word);
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvShow.setVisibility(View.GONE);
                    }
                }, 2000);
            }
        });
    }
}
