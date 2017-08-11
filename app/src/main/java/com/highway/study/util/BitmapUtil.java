package com.highway.study.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.highway.study.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author JH
 * @date 2017/5/31
 */


public class BitmapUtil {
    private static final String TAG = "BitmapUtil";

    /**
     * 获取当前屏幕截图，包括状态栏
     *
     * @param activity Activity
     * @return Bitmap
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        try {
            View decorView = activity.getWindow().getDecorView();
            decorView.setDrawingCacheEnabled(true);
            decorView.buildDrawingCache();
            Bitmap drawingCache = decorView.getDrawingCache();

            DisplayMetrics displayMetrics = getScreenDisplayMetrics(activity);
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            Bitmap bitmap;
            if (drawingCache != null) {
                bitmap = Bitmap.createBitmap(drawingCache, 0, 0, width, height);
                decorView.setDrawingCacheEnabled(false);
            } else {
                return null;
            }
            decorView.destroyDrawingCache();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取当前屏幕截图，不包括状态栏
     *
     * @param activity Activity
     * @return Bitmap
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        try {
            View decorView = activity.getWindow().getDecorView();
            decorView.setDrawingCacheEnabled(true);
            decorView.buildDrawingCache();
            Bitmap drawingCache = decorView.getDrawingCache();
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            DisplayMetrics displayMetrics = getScreenDisplayMetrics(activity);
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;

            Bitmap bitmap;
            if (drawingCache != null) {
                Log.e(TAG, "屏幕的宽度" + width);
                Log.e(TAG, "图片的宽度" + drawingCache.getWidth());
                Log.e(TAG, "屏幕的高度" + height);
                Log.e(TAG, "图片的高度" + drawingCache.getHeight());
                bitmap = Bitmap.createBitmap(drawingCache, 0, statusBarHeight, width, height - statusBarHeight);
                decorView.setDrawingCacheEnabled(false);
                Log.e(TAG, "生成图片大小" + bitmapToByteArray(bitmap, false).length / 1024);
            } else {
                return null;
            }
            decorView.destroyDrawingCache();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成带二维码的图片
     *
     * @param activity
     * @return
     */
    public static Bitmap shareBottomView(Activity activity) {
        try {
            View bottomView = LayoutInflater.from(activity).inflate(R.layout.share_image_layout, null);
            ImageView imageView = (ImageView) bottomView.findViewById(R.id.qrcode);
            Bitmap bitmap = QRCodeHelper.createQRCode("http://t.9188.com", Color.parseColor("#fff6ee"), Color.parseColor("#7f433e"));
            imageView.setImageBitmap(bitmap);
            bottomView.setDrawingCacheEnabled(true);
            DisplayMetrics dm = getScreenDisplayMetrics(activity);
            bottomView.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(Utility.dip2px(activity, 100), View.MeasureSpec.EXACTLY));

            bottomView.layout(0, 0, bottomView.getMeasuredWidth(), bottomView.getMeasuredHeight());
            bottomView.buildDrawingCache();
            Bitmap cacheBitmap = Bitmap.createBitmap(bottomView.getDrawingCache());
            if (cacheBitmap != null) {
                return cacheBitmap;
            }
            bottomView.setDrawingCacheEnabled(false);
            bottomView.destroyDrawingCache();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取屏幕信息
     *
     * @param activity
     * @return
     */
    public static DisplayMetrics getScreenDisplayMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    /**
     * 合成两个图片
     */
    public static Bitmap doMerge(Bitmap first, Bitmap second) {
        if (first == null || second == null) {
            return null;
        }
        Bitmap newb = Bitmap.createBitmap(first.getWidth(), first.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas();
        canvas.setBitmap(newb);
        canvas.drawColor(Color.parseColor("#FFFFFF"));

        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight() - second.getHeight(), null);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        return newb;
    }


    /**
     * 图片的缩放方法
     *
     * @param bitmap  ：源图片资源
     * @param maxSize ：图片允许最大空间  单位:KB
     * @return
     */
    public static Bitmap getZoomImage(Bitmap bitmap, double maxSize) {
        if (null == bitmap) {
            return null;
        }
        if (bitmap.isRecycled()) {
            return null;
        }

        // 单位：从 Byte 换算成 KB
        double currentSize = bitmapToByteArray(bitmap, false).length / 1024;
        // 判断bitmap占用空间是否大于允许最大空间,如果大于则压缩,小于则不压缩
        while (currentSize > maxSize) {
            // 计算bitmap的大小是maxSize的多少倍
            double multiple = currentSize / maxSize;
            // 开始压缩：将宽带和高度压缩掉对应的平方根倍
            // 1.保持新的宽度和高度，与bitmap原来的宽高比率一致
            // 2.压缩后达到了最大大小对应的新bitmap，显示效果最好
            bitmap = getZoomImage(bitmap, bitmap.getWidth() / Math.sqrt(multiple), bitmap.getHeight() / Math.sqrt(multiple));
            currentSize = bitmapToByteArray(bitmap, false).length / 1024;
        }
        return bitmap;
    }

    /**
     * 图片的缩放方法
     *
     * @param orgBitmap ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }

        // 获取图片的宽和高
        float width = orgBitmap.getWidth();
        float height = orgBitmap.getHeight();
        // 创建操作图片的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(orgBitmap, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * bitmap转换成byte数组
     *
     * @param bitmap
     * @param needRecycle
     * @return
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap, boolean needRecycle) {
        if (null == bitmap) {
            return null;
        }
        if (bitmap.isRecycled()) {
            return null;
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bitmap.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return result;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize += 2;
            }
        }
        return inSampleSize;
    }


    public static LruCache lruCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
//        cache.put()
//        cache.remove()
//        cache.get()
        return cache;
    }

    public static DiskLruCache getLruCache(Context context) {
        long DISK_CACHE_SIZE = 1024 * 1024 * 50;
        File disCacheDir = new File(context.getCacheDir(), "bitmap");
        if (!disCacheDir.exists()) {
            disCacheDir.mkdirs();
        }
        try {
            DiskLruCache cache = DiskLruCache.open(disCacheDir, 1, 1, DISK_CACHE_SIZE);
            return cache;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String hashKeyFromUrl(String url) {
        String cacheKey;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            cacheKey = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            String hex = Integer.toHexString(0xFF & digest[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 获取磁盘中图片流
     */
    public static OutputStream getStreamFromDis(String url, Context context) {
        String key = hashKeyFromUrl(url);
        try {
            DiskLruCache.Editor editor = getLruCache(context).edit(key);
            if (editor != null) {
//                OutputStream outputStream = editor.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
