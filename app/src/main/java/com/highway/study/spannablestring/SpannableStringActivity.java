package com.highway.study.spannablestring;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.highway.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用SpannableString打造绚丽多彩的文本显示效果
 * <p>
 * <p>
 * setSpan(Object what, int start, int end, int flags)方法需要用户输入四个参数，
 * what表示设置的格式是什么，可以是前景色、背景色也可以是可点击的文本等等，
 * start表示需要设置格式的子字符串的起始下标，同理end表示终了下标，flags共有四种属性：
 * 1、Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终了下标，包括起始下标
 * 2、Spanned.SPAN_INCLUSIVE_INCLUSIVE 从起始下标到终了下标，同时包括起始下标和终了下标
 * 3、Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 从起始下标到终了下标，但都不包括起始下标和终了下标
 * 4、Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终了下标，包括终了下标
 */
public class SpannableStringActivity extends AppCompatActivity {

    @Bind(R.id.foreground)
    TextView fgtext;
    @Bind(R.id.background)
    TextView bgtext;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string);
        ButterKnife.bind(this);
//        foreground();
//        background();
//        relativeSize();
//        strikethrough ();
//        underline();
//        superscript();
//        subscript ();
//        style();
//        image();
        handler.sendEmptyMessageDelayed(0x001, 150);
    }

    /**
     * ForegroundColorSpan
     */
    private void foreground() {
        SpannableString string = new SpannableString("设置文字的前景色为淡蓝色");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        string.setSpan(colorSpan, 9, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        fgtext.setText(string);
    }

    /**
     * BackgroundColorSpan
     */
    private void background() {
        SpannableString string = new SpannableString("设置文字的前景色为淡蓝色");
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.parseColor("#0099EE"));
        string.setSpan(colorSpan, 9, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        bgtext.setText(string);
    }

    /**
     * RelativeSizeSpan，设置文字相对大小，在TextView原有的文字大小的基础上，相对设置文字大小
     */
    private void relativeSize () {
        SpannableString string = new SpannableString("设置文字的前景色为淡蓝色");
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);
        string.setSpan(sizeSpan, 9, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        bgtext.setText(string);
    }

    /**
     * StrikethroughSpan 删除线
     */

    private void strikethrough () {
        SpannableString string = new SpannableString("设置文字的前景色为淡蓝色");
        StrikethroughSpan sizeSpan = new StrikethroughSpan();
        string.setSpan(sizeSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        bgtext.setText(string);
    }

    /**
     * UnderlineSpan，为文本设置下划线
     */
    private void underline() {
        SpannableString spannableString = new SpannableString("为文字设置下划线");
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        bgtext.setText(spannableString);
    }

    /**
     * SuperscriptSpan，设置上标
     */
    private void superscript () {
        SpannableString string = new SpannableString("为文字设置下划线");
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
        string.setSpan(sizeSpan, 5, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        string.setSpan(superscriptSpan, 5, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        bgtext.setText(string);
    }

    /**
     * SubscriptSpan，设置下标
     */
    private void subscript () {
        SpannableString string = new SpannableString("为文字设置下划线");
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
        string.setSpan(sizeSpan, 5, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SubscriptSpan superscriptSpan = new SubscriptSpan();
        string.setSpan(superscriptSpan, 5, string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        bgtext.setText(string);
    }

    /**
     * StyleSpan，为文字设置风格（粗体、斜体）
     */
    private void style() {
        SpannableString spannableString = new SpannableString("为文字设置粗体、斜体风格");
        StyleSpan styleSpan_B  = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I  = new StyleSpan(Typeface.ITALIC);
        spannableString.setSpan(styleSpan_B, 5, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_I, 8, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        bgtext.setHighlightColor(Color.parseColor("#36969696"));
        bgtext.setText(spannableString);
    }

    /**
     * ImageSpan，设置文本图片
     */
    private void image() {
        SpannableString spannableString = new SpannableString("在文本中添加表情（表情）");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, 6, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        bgtext.setText(spannableString);
    }

    /**
     * ClickableSpan 点击
     */

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    SpannableString string = new SpannableString("设置文字的前景色为淡蓝色");
                    RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);
                    string.setSpan(sizeSpan, pos, pos + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    bgtext.setText(string);
                    pos++;
                    if (pos >= bgtext.getText().length()) {
                        pos = 0;
                    }
                    Message ms = Message.obtain();
                    ms.what = 0x001;
                    handler.sendEmptyMessageDelayed(0x001, 150);
                    break;
            }
        }
    };
}
