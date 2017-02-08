package com.highway.study.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.highway.study.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_service)
    public void startClick() {
        startService(new Intent(this, MyService.class));
    }

    @OnClick(R.id.stop_service)
    public void stopClick() {
        stopService(new Intent(this, MyService.class));
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @OnClick(R.id.bind_service)
    public void bindClick() {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.unbind_service)
    public void unBindClick() {
        unbindService(connection);
    }

    @OnClick(R.id.intentService)
    public void startIntentService() {
        startActivity(new Intent(this, IntentServiceActivity.class));
    }
}
