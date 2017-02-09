package com.highway.study.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.highway.study.util.Utility;


/**
 * 监听网络变化
 */
public class NetBroadCasetreceiver extends BroadcastReceiver {
    public NetBroadCasetreceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isAlive = Utility.isNetworkConnected(context);
        if (isAlive) {
            // TODO: 2017/2/9 网络连接上
        } else {
            // 网络异常
            Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
        }
    }
}
