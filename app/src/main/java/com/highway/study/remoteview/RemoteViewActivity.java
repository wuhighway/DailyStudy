package com.highway.study.remoteview;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import com.highway.study.MainActivity;
import com.highway.study.R;
import com.highway.study.anim.AnimActivity;


public class RemoteViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RemoteViewActivity";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    private static int sId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_view);
        initView();
    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        if (v == mButton1) { // 系统通知样式
            builder.setSmallIcon(R.mipmap.icon_notice)
                    .setContentTitle("Notification")
                    .setContentText("this is test notification")
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
            Intent intent = new Intent(this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            manager.notify(0, builder.build());
        } else if (v == mButton2) {
            builder.setTicker("hello")
                    .setSmallIcon(R.mipmap.icon_notice) // 必须要设置小图标否则会报错
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setOngoing(true) // 设置通知在状态栏中不能左右滑动
                    .setDefaults(Notification.DEFAULT_ALL); // 设置震动、声音等为默认
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.coustom_notification_layout);
            remoteViews.setTextViewText(R.id.content, "this is coustom content");
            remoteViews.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
            PendingIntent openintent = PendingIntent.getActivity(this,
                    0, new Intent(this, AnimActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.image, openintent);
            builder.setContent(remoteViews);
            builder.setContentIntent(pendingIntent);
            manager.notify(3, builder.build());
        } else if (v == mButton3) { // 更新通知进度

            builder.setSmallIcon(R.mipmap.icon_notice)
                    .setContentTitle("Download")
                    .setContentText("download progression");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    int incr;
                    for (incr = 0; incr <= 100; incr += 10) {
                        builder.setProgress(100, incr, false);
                        manager.notify(1, builder.build());

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    builder.setContentText("Download complete");
                    manager.notify(1, builder.build());
                }
            }).start();
        }
    }


}
