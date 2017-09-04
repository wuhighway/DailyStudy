package com.highway.study.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.highway.study.R;
import com.highway.study.util.Logger;


public class SensorActivity extends AppCompatActivity {

    private static final String TAG = "SensorActivity";
    private SensorManager manager;
    private Sensor sensor1;
    private Sensor sensor2;
    private Sensor sensor3;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        textView = (TextView) findViewById(R.id.text);
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor1 = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensor2 = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor3 = manager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        manager.registerListener(registerListener1, sensor1, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(registerListener2, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
        manager.requestTriggerSensor(mTriggerEventListener, sensor3);
    }

    SensorEventListener registerListener1 = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Logger.e(TAG, "onSensorChanged");
            textView.setText(String.format("步数:%1$s", event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Logger.e(TAG, "onAccuracyChanged");
        }
    };

    SensorEventListener registerListener2 = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
//            Logger.e(TAG, "onSensorChanged");
            textView1.setText(String.format("X轴加速度:%1$s", event.values[0]));
            textView2.setText(String.format("Y轴加速度:%1$s", event.values[1]));
            textView3.setText(String.format("Z轴加速度:%1$s", event.values[2]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//            Logger.e(TAG, "onAccuracyChanged");
        }
    };


    TriggerEventListener mTriggerEventListener = new TriggerEventListener() {
        @Override
        public void onTrigger(TriggerEvent event) {
            Logger.e(TAG, "NAME = " + event.sensor.getName());
            Logger.e(TAG, "TIME = " + event.timestamp);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterListener(registerListener1);
    }
}
