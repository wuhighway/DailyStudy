package com.highway.studydemo;

/**
 * @author JH
 * @date 2017/7/24
 */


public class TestJava {
    // 用final修饰，必须初始化，并且不能修改。
    private final int n = 1;
    // 被这个类的所有实例共享
    public static int i = 1;
    private int j;

    public int getJ() {
        return j;
    }

    public void setJ() {
        this.j = i;
        i++;
    }
    // 方法签名：方法名和参数
//    public void getJ() {}

    //    public static void main(String[] args) {
//        TestJava testJava = new TestJava();
//        testJava.setJ();
//        TestJava testJava1 = new TestJava();
//        System.out.println(testJava1.i);
//    }
    static {
        System.out.println("time....");
//        System.exit(0);
    }
}
