package com.highway.study.coustomview.loadingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.highway.study.R;

/**
 * Created by JH
 * on 2017/2/28.
 * 资源文件(drawable/mipmap/raw):
 * Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.raw.bitmap);
 * <p>
 * 资源文件(assets):
 * Bitmap bitmap=null;
 * try {
 * InputStream is = mContext.getAssets().open("bitmap.png");
 * bitmap = BitmapFactory.decodeStream(is);
 * is.close();
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * <p>
 * 内存卡文件:
 * <p>
 * Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/bitmap.png");
 * 网络文件:
 * <p>
 * // 此处省略了获取网络输入流的代码
 * Bitmap bitmap = BitmapFactory.decodeStream(is);
 * is.close();
 */

public class CheckView extends View {

    private int mWidth, mHeight;
    private Paint mPaint;
    private Bitmap okBitmap;

    private int animCurrentPage = -1;       // 当前页码
    private int animMaxPage = 13;           // 总页数
    private int animDuration = 2000;         // 动画时长
    private Handler mHandler;           // handler
    private static int ANIM_NULL = 0; // 未开启状态
    private static int ANIM_CHECK = 1; // 开启
    private static int ANIM_UNCHECK = 2; // 还原
    boolean isCheck;
    private int animState = ANIM_NULL;      // 动画状态

    public CheckView(Context context) {
        this(context, null);
    }

    public CheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(0xffFF5317);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        okBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.check);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (animState == ANIM_CHECK) {
                    animCurrentPage++;
                } else if (animState == ANIM_UNCHECK) {
                    animCurrentPage--;
                }
                if (animCurrentPage < animMaxPage && animCurrentPage >= 0) {
                    invalidate();
//                    animCurrentPage ++;
                    mHandler.sendEmptyMessageDelayed(0, animDuration / animMaxPage);
                } else {

                }
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 移动坐标系到画布中央
        canvas.translate(mWidth / 2, mHeight / 2);

        // 绘制背景圆形
        canvas.drawCircle(0, 0, 240, mPaint);

        // 得出图像边长
        int sideLength = okBitmap.getHeight();

        Rect src = new Rect(sideLength * animCurrentPage, 0, sideLength * (animCurrentPage + 1), sideLength);
        Rect dst = new Rect(-200, -200, 200, 200);

        canvas.drawBitmap(okBitmap, src, dst, null);
    }

    /**
     * 选择
     */
    public void check() {
//        animCurrentPage = 0;
        if (animState != ANIM_NULL || isCheck)
            return;
        mHandler.sendEmptyMessageDelayed(0, animDuration / animMaxPage);
        isCheck = true;
        animState = ANIM_CHECK;
    }
}
