package com.highway.study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.highway.study.coustomview.loadingview.CoustomActivity;
import com.highway.study.takephoto.TakePhotoActivity;
import com.highway.study.ui.viewflipper.ViewFlipperTestActivity;
import com.highway.study.ui.viewflipper.datepicker.DatePickerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.viewflipper).setOnClickListener(this);
        findViewById(R.id.takephoto).setOnClickListener(this);
        findViewById(R.id.datepick).setOnClickListener(this);
        findViewById(R.id.coustom).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewflipper:
                startActivity(new Intent(this, ViewFlipperTestActivity.class));
                break;
            case R.id.takephoto:
                startActivity(new Intent(this, TakePhotoActivity.class));
                break;
            case R.id.datepick:
                startActivity(new Intent(this, DatePickerActivity.class));
                break;
            case R.id.coustom:
                startActivity(new Intent(this, CoustomActivity.class));
        }
    }
}
