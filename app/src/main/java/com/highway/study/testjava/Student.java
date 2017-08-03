package com.highway.study.testjava;

/**
 * @author JH
 * @date 2017/7/31
 */


public class Student extends Person {
    private String id;

    public Student() {
        this.id = "1000";
    }

    public void study() {
    }
    private void speek(String text, int index) {
        System.out.println(text + index);
    }
}
