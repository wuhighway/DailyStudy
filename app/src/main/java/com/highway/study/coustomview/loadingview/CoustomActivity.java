package com.highway.study.coustomview.loadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.highway.study.R;

public class CoustomActivity extends AppCompatActivity {

    MatchTechniqueView techniqueView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coustom);
//        final CheckView pieView = (CheckView) findViewById(R.id.pieView);

//        List<PieData> pieDatas = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            PieData data = new PieData();
//            data.setValue(i + 3);
//            data.setName("item" + i);
//            pieDatas.add(data);
//        }
//        pieView.setData(pieDatas);
//        final Win8Search view = (Win8Search)findViewById(R.id.loading_view);
//        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pieView.check();
//            }
//        });
        //找到组件
//        LeafLoadingView loading = (LeafLoadingView) findViewById(R.id.loading);
//        //设置进度
//        loading.setProgress(50);
        //PS：作为一个这么高大上的自定义View，当然不会只有一个setProgress方法了，关于其他用法，请看后续的说明文档.
//        FootballTeamPkData pkData = new FootballTeamPkData();
//        pkData.setTitle("例子");
//        pkData.setHvalue("98");
//        pkData.setGvalue("102");
//        techniqueView = (MatchTechniqueView) findViewById(R.id.teach);
//        techniqueView.setTeachData(pkData);
    }
}
