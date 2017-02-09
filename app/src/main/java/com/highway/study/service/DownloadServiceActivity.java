package com.highway.study.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.highway.study.R;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 断点下载文件
 */
public class DownloadServiceActivity extends AppCompatActivity {

    public static final String UPDATE_ACTION = "update_progress_action";
    public static final String UPDATE_NAME = "progress";
    DownloadService.DownloadBinder binder;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    DownReceiver downReceiver;

    class DownReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (UPDATE_ACTION.equals(intent.getAction())) {
                int progress = intent.getIntExtra(UPDATE_NAME, 0);
                progressBar.setProgress(progress);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service);
        ButterKnife.bind(this);
        startService(new Intent(this, DownloadService.class));
        bindService(new Intent(this, DownloadService.class), connection, BIND_AUTO_CREATE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);
        downReceiver = new DownReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(downReceiver, filter);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @OnClick(R.id.start_down)
    public void start() {
        if (binder == null)
            return;
        String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
        binder.startDownload(url);
    }

    @OnClick(R.id.cancel_down)
    public void cancel() {
        if (binder == null)
            return;
        binder.cancelDownload();
    }

    @OnClick(R.id.paused_down)
    public void paused() {
        if (binder == null)
            return;
        binder.pauseDownload();
    }

    static class WeakHandler extends Handler {
        WeakReference<Activity> weakReference;

        public WeakHandler(Activity activity) {
            this.weakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() == null) {
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        // 取消注册
        LocalBroadcastManager.getInstance(this).unregisterReceiver(downReceiver);
    }
}
