package com.highway.study.keyboard;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.highway.study.R;

public class KeyBoardActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private EditText mUsernameEt;
    private EditText mPasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);
        mPasswordEt = (EditText) findViewById(R.id.editText2);
        mPasswordEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inputType = mPasswordEt.getInputType();
                mPasswordEt.setInputType(InputType.TYPE_NULL);
                new KeyboardUtil(KeyBoardActivity.this, KeyBoardActivity.this, mPasswordEt).showKeyboard();
                mPasswordEt.setInputType(inputType);
                return false;
            }
        });
    }
}
