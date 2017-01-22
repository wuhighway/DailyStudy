package com.highway.study.ui.viewflipper.datepicker;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.highway.study.R;

public class DatePickerActivity extends AppCompatActivity {

    /**
     * 当前月
     */
    int mCurrentMonth = 6;
    /**
     * 当前年
     */
    int mCurrenYears = 2016;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        findViewById(R.id.bt_datedialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    /**
     * 时间选择dialog
     */

    private void showDatePickerDialog() {
        final YmDatePickerDialog m = new YmDatePickerDialog(DatePickerActivity.this, null, mCurrenYears, mCurrentMonth - 1, 1);
        m.setCancelable(true);
        m.setCanceledOnTouchOutside(true);
        m.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCurrentMonth = m.getDatePicker().getMonth() + 1;
                        mCurrenYears = m.getDatePicker().getYear();
                        String mon = mCurrentMonth + "";
                        if (mCurrentMonth <= 9) {
                            mon = "0" + mon;
                        }
                        String year = ("" + mCurrenYears).substring(2);
                        if (!TextUtils.isEmpty(year)) {

                        }
                    }
                });
        m.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m.dismiss();
                    }
                });
        m.show();
    }
}
