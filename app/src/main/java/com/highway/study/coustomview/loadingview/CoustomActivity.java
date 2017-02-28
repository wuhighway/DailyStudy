package com.highway.study.coustomview.loadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.highway.study.R;
import com.highway.study.coustomview.loadingview.bean.PieData;

import java.util.ArrayList;
import java.util.List;

public class CoustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coustom);
        PieView pieView = (PieView) findViewById(R.id.pieView);
        List<PieData> pieDatas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PieData data = new PieData();
            data.setValue(i + 3);
            data.setName("item" + i);
            pieDatas.add(data);
        }
        pieView.setData(pieDatas);
//        final Win8Search view = (Win8Search)findViewById(R.id.loading_view);
//        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.startAnimation();
//            }
//        });
    }
}
