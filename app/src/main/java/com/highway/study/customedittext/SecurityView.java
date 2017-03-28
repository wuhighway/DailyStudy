package com.highway.study.customedittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.highway.study.R;

/**
 * @author JH
 * @date 2017/3/28
 * 思路：
 * 1、用一个透明的EditText与四个TextView重叠，并给TextView设置默认背景
 * 2、监听EditText文本变化，获取输入内容，给TextView赋值并改变TextView背景
 * 3、4个TextView有值后添加输入完成回调，监听删除键添加删除回调
 */
public class SecurityView extends RelativeLayout {

    private EditText editText;
    private TextView[] TextViews;
    /** 存储输入的内容*/
    private StringBuffer stringBuffer = new StringBuffer();
    /** 当前输入位数*/
    private int count = 0;
    /** 输入的内容*/
    private String inputContent;

    public SecurityView(Context context) {
        this(context, null);
    }

    public SecurityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_security_code, this);
        TextViews = new TextView[4];
        editText = (EditText) findViewById(R.id.item_edittext);
        TextViews[0] = (TextView) findViewById(R.id.item_code_iv1);
        TextViews[1] = (TextView) findViewById(R.id.item_code_iv2);
        TextViews[2] = (TextView) findViewById(R.id.item_code_iv3);
        TextViews[3] = (TextView) findViewById(R.id.item_code_iv4);

        editText.setCursorVisible(false);//将光标隐藏
        setListener(); // 设置输入监听
    }

    /**
     * 设置editText输入文字监听和删除按键箭筒
     */
    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if (stringBuffer.length() > 3) { //输入内容超过的时候不做处理清空editText
                        editText.setText("");
                        return;
                    } else {
                        stringBuffer.append(s.toString());
                        editText.setText(""); // 获取内容后将每次的输入内容清空
                        count = stringBuffer.length(); // 记录当前的内容位数
                        inputContent = stringBuffer.toString(); // 获取当前输入的所有内容
                        if (stringBuffer.length() == 4 && inputCompleteListener != null) { // 当输入达到指定的位数后完成输入回调
                            inputCompleteListener.inputComplete();
                        }

                        for (int i = 0; i < stringBuffer.length(); i++) { // 将输入的内容设置到对用的TextView上并且改变TextView的背景
                            TextViews[i].setText(String.valueOf(inputContent.charAt(i)));
                            TextViews[i].setBackgroundResource(R.mipmap.bg_verify_press);
                        }
                    }
                }
            }
        });
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && KeyEvent.ACTION_DOWN == event.getAction()) {
                    deleteStr();
                    return true;
                }

                return false;
            }
        });
    }

    /**
     * 删除输入的内容
     */
    public void deleteStr() {
        if (stringBuffer.length() > 0 && count > 0) {
            stringBuffer.delete((count - 1), count); // 删除最后一个内容
            count--;
            inputContent = stringBuffer.toString();
            TextViews[stringBuffer.length()].setText(""); // 清空内容
            TextViews[stringBuffer.length()].setBackgroundResource(R.mipmap.bg_verify); // 还原背景
            if (inputCompleteListener != null)
                inputCompleteListener.deleteContent(true);// 删除回调
        }

    }

    /**
     * 获取输入的内容
     *
     * @return inputContent
     */
    public String getInputContent() {
        return inputContent;
    }

    /** 监听回调*/
    private InputCompleteListener inputCompleteListener;

    /***
     * 设置监听
     * @param inputCompleteListener
     */
    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    /**
     * 回调监听
     */
    public interface InputCompleteListener {

        /**
         * 输入完成时回调该方法
         */
        void inputComplete();

        /**
         * 删除时回调该方法
         * @param isDelete
         */
        void deleteContent(boolean isDelete);
    }
}
