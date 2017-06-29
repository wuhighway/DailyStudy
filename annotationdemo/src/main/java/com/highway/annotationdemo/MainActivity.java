package com.highway.annotationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * 注解简单使用
 *
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.text)
    private TextView text;
    @ViewInject(R.id.button)
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ViewInjectUtils.inject(this);
    }

    @OnClick(R.id.text)
    private void onClick(View view){
        text.setText("我被点击了！！！");
    }
}
