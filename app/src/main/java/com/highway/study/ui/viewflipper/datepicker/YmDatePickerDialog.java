package com.highway.study.ui.viewflipper.datepicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.highway.study.R;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by JH on 2017/1/22.
 * 日期选择器，继承自系统控件 datePicterDialog，
 * 隐藏系统控件标题、隐藏日期选择
 */

public class YmDatePickerDialog extends DatePickerDialog {
    private static final String TAG = "YmDatePickerDialog";
    /** 日期格式 */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /** 年 */
    public static final int YEAR = 1;
    /** 月 */
    public static final int MONTH = 2;
    /** 日 */
    public static final int DAY = 3;
    /** 日历控件 */
    private final DatePicker mDatePicker;

    public YmDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, R.style.date_picker_style, callBack, year, monthOfYear, dayOfMonth);
        this.setTitle(""); // 去掉标题
        mDatePicker = this.getDatePicker();
        setSpecificViewVisibility(getSpecificFieldName(DAY), View.GONE); // 设置日不可见
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        this.setTitle(""); // 去掉标题
    }

    @Override
    public void show() {
        super.show();
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(false);
        setDialogWidth(); // 重新设置dialog的宽度
    }

    /**
     * 获取【年、月、日】属性名
     */
    public String getSpecificFieldName(int type){
        String fieldname = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) { // Android 4.0 以上
            switch (type) {
                case YEAR: // 年
                    fieldname = "mYearSpinner";
                    break;
                case MONTH: // 月
                    fieldname = "mMonthSpinner";
                    break;
                case DAY: // 日
                    fieldname = "mDaySpinner";
                    break;
            }
        } else { // Android 4.0 以下
            switch (type) {
                case YEAR: // 年
                    fieldname = "mYearPicker";
                    break;
                case MONTH: // 月
                    fieldname = "mMonthPicker";
                    break;
                case DAY: // 日
                    fieldname = "mDayPicker";
                    break;
            }
        }
        return fieldname;
    }

    /**
     * 设置【年、月、日】的可见性
     * 通过反射获取年、月、日控件
     */
    public void setSpecificViewVisibility(String fieldName, int visibility) {
        Class clazz = mDatePicker.getClass();
        Field field;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // Android 5.0 以上
                field = clazz.getDeclaredField("mDelegate");
                field.setAccessible(true);
                Object delegateObject = field.get(mDatePicker);
                Class[] declaredClasses = clazz.getDeclaredClasses();
                for (Class declaredClazz : declaredClasses) {
                    if ("android.widget.DatePicker$DatePickerSpinnerDelegate".equals(declaredClazz.getName())) {
                        clazz = declaredClazz;
                        field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        NumberPicker numberPicker = (NumberPicker) field.get(delegateObject);
                        numberPicker.setVisibility(visibility);
                        break;
                    }
                }
            } else { // Android 5.0 以下
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                NumberPicker numberPicker = (NumberPicker) field.get(mDatePicker);
                numberPicker.setVisibility(visibility);
            }
        } catch (SecurityException | NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
        }
    }

    /**
     * 设置起始时间为当前日期
     */
    public void setStartFromCurrentDate(){
        mDatePicker.setMinDate(System.currentTimeMillis());
    }

    /**
     * 设置起止时间(日期格式：yyyy-MM-dd)
     */
    public void setStartAndEndDate(String startDate, String endDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            if (!TextUtils.isEmpty(startDate)) {
                mDatePicker.setMinDate(dateFormat.parse(startDate).getTime());
            }
            if (!TextUtils.isEmpty(endDate)) {
                mDatePicker.setMaxDate(dateFormat.parse(endDate).getTime());
            }
        } catch (ParseException e) {
        }
    }

    /**
     * 设置对话框的宽度
     */
    private void setDialogWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth();
        window.setAttributes(lp);
    }

}
