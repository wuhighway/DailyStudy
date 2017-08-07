package com.highway.study.testjava.annotations;

import java.lang.reflect.Method;

/**
 * @author JH
 * @date 2017/8/7
 */


public class UseCaseTrancer {

    public static void trackUseCases(Class cl) {
        for (Method m : cl.getDeclaredMethods()) {
            System.out.println("method name = " + m.getName());
            UseCase us = m.getAnnotation(UseCase.class);
            if (us != null) {
                System.out.println("value = " + us.value() + " description = " +
                us.description());
            }
        }
    }

    public static void main(String[] args) {
        trackUseCases(PasswordUtils.class);
    }
}
