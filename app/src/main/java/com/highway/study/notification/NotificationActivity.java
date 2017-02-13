package com.highway.study.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.highway.study.R;
import com.highway.study.spannablestring.SpannableStringActivity;

import java.io.File;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Intent intent = new Intent(this, SpannableStringActivity.class);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("this is content title") // 标题
                .setContentText("this is content text") // 正文内容
                .setWhen(System.currentTimeMillis()) // 创建的时间
                .setSmallIcon(R.mipmap.ic_launcher) // 通知的小图标，显示在系统状态栏
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)) // 通知大图标
                .setContentIntent(pendingIntent)
                .setTicker("this is content text") // 显示在系统状内容
                .setAutoCancel(true)
                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))// 指定通知声音
                .setVibrate(new long[] {0, 1000, 1000, 1000}) // 设置震动 ，权限VINRATE
                .setLights(Color.GREEN, 1000, 1000) // 设置led灯闪烁
//                .setDefaults(NotificationCompat.DEFAULT_ALL) // 设置系统默认属性
                .build();
        manager.notify(1, notification);
    }


    //长文字
    private void largeText() {
        Notification notification = new NotificationCompat.Builder(this)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("you are good boy!" +
                        "you are good boy!you are good boy!you are good boy!you are good boy!")) // 大文本
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))) // 大图片
                .setPriority(NotificationCompat.PRIORITY_MAX) // 设置通知的重要程度
                .build();
    }
}
