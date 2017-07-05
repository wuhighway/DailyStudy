package com.highway.study.androidbase;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.highway.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    @Bind(R.id.bind)
    Button bind;
    @Bind(R.id.start)
    Button start;
    @Bind(R.id.stop)
    Button stop;
    @Bind(R.id.unbind)
    Button unbind;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected....") ;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected....") ;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
    }

    @OnClick(value = {R.id.bind, R.id.start, R.id.unbind, R.id.stop})
    public void onclick(View view){
        switch (view.getId()) {
            case R.id.bind:
                bind();
                break;
            case R.id.start:
                start();
                break;
            case R.id.unbind:
                unbind();
                break;
            case R.id.stop:
                stop();
                break;
        }
    }

    private void stop() {
        Intent intent = new Intent(this, BaseService.class);
        stopService(intent);
    }

    private void unbind() {
        unbindService(conn);
    }

    private void bind() {
        Intent intent = new Intent(this, BaseService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
        startService(intent);
    }

    private void start() {
        Intent intent = new Intent(this, BaseService.class);
        startService(intent);
    }
}
