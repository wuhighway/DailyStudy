package com.highway.study.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by JH
 * on 2017/2/6.
 */

public class Utity {
    private static Toast toast;


    public static void showToast(Context context, String desc) {
        if (toast == null) {
            toast = Toast.makeText(context, desc, Toast.LENGTH_SHORT);
        } else {
            toast.setText(desc);
        }
        toast.show();
    }

    /**
     * WeakReference handler
     */
    public static class WeakHandler extends Handler {
        public WeakReference<Activity> mActivityReference;

        public WeakHandler(Activity activity) {
            this.mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mActivityReference.get() == null) {
                return;
            }
        }
    }
}
