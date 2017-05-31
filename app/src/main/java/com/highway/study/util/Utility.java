package com.highway.study.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

    /**
     * 把内容复制到剪切板
     *
     * @param context 上下文
     * @param text    复制到剪切板的内容
     */
    public static boolean copyToClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.HONEYCOMB) {
            // api level < 11
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                clipboard.setText(text);
                return true;
            }
        } else {
            // api level >= 11
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                clipboard.setText(text);
                return true;
            }
        }
        return false;
    }

    /**
     * 读取粘帖板内容
     *
     * @param context context
     * @return 粘帖板内容
     */
    @SuppressLint("NewApi")
    public static String getClipBoardContent(Context context) {
        try {
            if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.HONEYCOMB) {
                // api level < 11
                @SuppressWarnings("deprecation")
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getApplicationContext().getSystemService(
                                Context.CLIPBOARD_SERVICE);
                return clipboard.getText().toString();
            } else {
                // api level >= 11
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getApplicationContext().getSystemService(
                                Context.CLIPBOARD_SERVICE);
                if (clipboard != null) {
                    return clipboard.getText().toString();
                }
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * 判断触摸是否在当前view的范围内
     */
    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view == null || !view.isShown()) {
            return false;
        }
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x
                || ev.getRawX() > (x + view.getWidth())
                || ev.getRawY() < y
                || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    /**
     * dip to px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // SUPPRESS CHECKSTYLE
    }
}
