package com.highway.annotationdemo;


/**
 * @author JH
 * @date 2017/6/29
 */
public class Student {
    public String stu_name;
    public String stu_id;
    public int stu_age;
}

//转换为
//        {class:"com.robert.processor.Student",
//        fields:
//        {
//        stu_name:"java.lang.String",
//        stu_id:"java.lang.String",
//        stu_age:"java.lang.Integer"
//        }
//        }

