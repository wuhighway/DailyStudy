package com.highway.study.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by JH
 * on 2017/2/6.
 */

public class Utility {

    private static final String TAG = "Utility";
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

    /**
     * 检测网络是否可用
     *
     * @param context context
     * @return 网络是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        boolean flag = false;
        if (null == context) {
            return false;
        }
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            }
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            flag = networkInfo != null && networkInfo.isAvailable();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            return false;
        }
        return flag;
    }
}
