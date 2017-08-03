package com.highway.study.testjava;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author JH
 * @date 2017/7/31
 *
 * getFields、getMethods、getConstructors返回类的所有的包括超类的公有成员
 *
 * getDeclaredMethods getDeclaredFields、getDeclaredConstructors返回类所有的公有的私有的类的成员，但不包括超类
 */

public class TestJavaDemo {

    public static void main(String[] args) {
        Student student = new Student();
        Class clazz = student.getClass();
        Class clazz2 = Student.class;
        try {
            String name = "com.highway.study.testjava.Student";
            Class clazz1 = Class.forName(name);
            Method[] methods = clazz1.getMethods();
            Method[] methods1 = clazz1.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("methods 1 name = " + method.getName());
            }

//            methods 1 name = study
//            methods 1 name = sleep
//            methods 1 name = eat
//            methods 1 name = wait
//            methods 1 name = wait
//            methods 1 name = wait
//            methods 1 name = equals
//            methods 1 name = toString
//            methods 1 name = hashCode
//            methods 1 name = getClass
//            methods 1 name = notify
//            methods 1 name = notifyAll

            for (Method method : methods1) {
                System.out.println("methods 2 name = " + method.getName());
            }

//            methods 2 name = study
//            methods 2 name = speek

            //获取私有属性
            try {
                Field field = clazz.getDeclaredField("id");
                field.setAccessible(true);
                Object id = field.get(student); //
                System.out.println("pre id = " + id);
                field.set(student, "2000"); // 重新设置属性
                Object current = field.get(student);
                System.out.println("pre id = " + current);

                System.out.println("============================================");

                // 调用私有方法
                Method method = clazz.getDeclaredMethod("speek", String.class, int.class);
                method.setAccessible(true);
                method.invoke(student, "i am student!", 4);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
