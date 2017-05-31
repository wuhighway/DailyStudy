package com.highway.study.screenshots;

import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.highway.study.R;
import com.highway.study.util.BitmapUtil;
import com.highway.study.util.Logger;

/**
 *
 * 截取当前屏幕显示view
 * @author JH
 * @date 2017/5/31
 */
public class ScreenshotsActivity extends AppCompatActivity {
    private static final String TAG = "ScreenshotsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshots);
    }

    /**
     * 生成分享图片
     */
    private void createShareImg() {
        final Bitmap currentPage = BitmapUtil.snapShotWithoutStatusBar(ScreenshotsActivity.this);
        final Bitmap rqCode = BitmapUtil.shareBottomView(ScreenshotsActivity.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap shareBitmap = null;
                try {
                    shareBitmap = BitmapUtil.doMerge(currentPage,
                            rqCode);
                    if (shareBitmap != null) {
                        Logger.e(TAG, "生成图片的大小" + BitmapUtil.bitmapToByteArray(shareBitmap, false).length / 1024);
                    }
//                    Message message = Message.obtain();
//                    message.obj = shareBitmap;
//                    message.what = SHARE_RESULT;
//                    mHandler.sendMessage(message);
                } catch (OutOfMemoryError e) {
//                    Message message = Message.obtain();
//                    message.obj = null;
//                    message.what = SHARE_RESULT;
//                    mHandler.sendMessage(message);
                }

            }
        }).start();
    }
}
