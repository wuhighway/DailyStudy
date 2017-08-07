package com.highway.study.testjava.annotations;

import android.text.TextUtils;

/**
 * @author JH
 * @date 2017/8/7
 */


public class PasswordUtils {
    @UseCase(value = 47, description = "this is demo")
    public boolean validatePassword(String password) {
        return TextUtils.isEmpty(password);
    }

    public String validate(String pass) {
        return pass;
    }
}
