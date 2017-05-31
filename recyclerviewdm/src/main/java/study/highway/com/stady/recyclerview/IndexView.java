package study.highway.com.stady.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JH
 * @date 2017/4/13
 */


public class IndexView extends View {
    private static final String TAG = "IndexView";
    private int itemWidth;
    private int itemHeight;
    private Paint paint;
    private int touchIndex = -1;
    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private List<String> wordsList;

    public IndexView(Context context) {
        super(context);
        init(context);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        float unit = context.getResources().getDisplayMetrics().density;
        paint = new Paint();
        paint.setColor(Color.WHITE);//设置颜色
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setTypeface(Typeface.DEFAULT_BOLD);//设置粗体字
        paint.setTextSize(14 * unit);
        Log.e(TAG, 14 * unit + "................");
        wordsList = Arrays.asList(words);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight() / wordsList.size();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < wordsList.size(); i++) {
            if (touchIndex == i) {
                paint.setColor(Color.parseColor("#ff222222"));
            } else {
                paint.setColor(Color.DKGRAY);
            }
            String word = wordsList.get(i);
            Rect rect = new Rect();
            paint.getTextBounds(word, 0, 1, rect);
            float x = itemWidth / 2 - rect.width() / 2;
            float y = itemHeight / 2 + rect.height() / 2 + i * itemHeight;
            canvas.drawText(word, 0, 1, x, y, paint);
        }
    }

    public void resetData(List<String> words) {
        wordsList = words;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y / itemHeight);
                touchIndex = index;
                invalidate();
                if (onIndexChangeListener != null && touchIndex < wordsList.size()) {
                    onIndexChangeListener.onIndexChange(wordsList.get(index));
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }


    /**
     * 字母下标索引变化的监听器
     */
    public interface OnIndexChangeListener {

        /**
         * 当字母下标位置发生变化的时候回调
         *
         * @param word 字母（A~Z）
         */
        void onIndexChange(String word);
    }

    private OnIndexChangeListener onIndexChangeListener;

    /**
     * 设置字母下标索引变化的监听
     *
     * @param onIndexChangeListener
     */
    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener) {
        this.onIndexChangeListener = onIndexChangeListener;
    }
}
