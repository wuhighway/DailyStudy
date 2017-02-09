package com.highway.study.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 拦截拨打电话
 */
public class CallReceiver extends BroadcastReceiver {
    public CallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String iphone = getResultData();
        String newPhone = "+86" + iphone;
        //把修改后的号码放回去
        setResultData(newPhone);
    }
}
