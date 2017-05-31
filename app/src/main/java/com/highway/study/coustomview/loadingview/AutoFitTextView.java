package com.highway.study.coustomview.loadingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.highway.study.R;

/**
 * Created by JH
 * on 2017/3/10.
 * 根据view的宽度自动调节字体的大小
 */

public class AutoFitTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = "AutoFitTextView";
    private static float DEFAULT_MIN_TEXT_SIZE = 15;
    private static float DEFAULT_MAX_TEXT_SIZE = 17;
    private TextPaint testPaint;
    private float minTextSize;
    private float maxTextSize;

    public AutoFitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoFitTextView);
        DEFAULT_MIN_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.AutoFitTextView_text_size_min, 6);
        DEFAULT_MAX_TEXT_SIZE = typedArray.getDimensionPixelSize(R.styleable.AutoFitTextView_text_size_max, 12);
//        DEFAULT_MIN_TEXT_SIZE = Utility.dip2px(context, 6);
//        DEFAULT_MAX_TEXT_SIZE = Utility.dip2px(context, 12);
        initialise();
    }

    private void initialise() {
        testPaint = new TextPaint();
        testPaint.set(this.getPaint());
        maxTextSize = this.getTextSize();
        minTextSize = DEFAULT_MIN_TEXT_SIZE;
    }

    /**
     * 根据文本的高度宽度调整字体的大小
     */
    private void refitText(String text, int textWidth, int textHeight) {
        if (textWidth > 0 && textHeight > 0) {
            //获取文本可填充的宽高
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            int availableHeight = textHeight - this.getPaddingBottom() - this.getPaddingTop();
            //计算出自适应的宽度
            int autoWidth = availableWidth;
            float mult = 1f;
            float add = 0;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                mult = getLineSpacingMultiplier();
                add = getLineSpacingExtra();
            }
            float trySize = maxTextSize;
            testPaint.setTextSize(trySize);
            int oldline = 1, newline = 1;
            while ((trySize > minTextSize)) {
                //测量出文本宽高。
                int displayWidth = (int) testPaint.measureText(text);
//                int displaHeight = round(testPaint.getFontMetricsInt(null) * mult + add);
                if (displayWidth < autoWidth) {
                    break;
                }
//                //显示的最大行数
//                newline = availableHeight / displaHeight;
//                //换行重新计算宽度
//                if (newline > oldline) {
//                    oldline = newline;
//                    autoWidth = availableWidth * newline;
//                    continue;
//                }
                //改变字体的大小
                trySize -= 1;
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }

                testPaint.setTextSize(trySize);
            }

            if (newline >= 2) {
                this.setSingleLine(true);
//                this.setMaxLines(newline);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        refitText(text.toString(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            refitText(this.getText().toString(), w, h);
        }
    }

    //
    public static int round(float value) {
        long lx = (long) (value * (65536 * 256f));
        return (int) ((lx + 0x800000) >> 24);
    }
}
