package com.highway.study.testjava.annotations;

/**
 * @author JH
 * @date 2017/8/7
 */

@DBTable(name = "MEMBER")
public class Member {
    @SQLString(30)
    String firstName;
    @SQLString(50)
    String laseName;
    @SQLInteger
    Integer age;
    @SQLString(value = 30,constraints = @Constraints(primaryKey = true))
    String handle;
    static int memberCount;
    public String getHandle() {return handle;}
    public String getFirstName() {return firstName;}
    public String getLaseName() {return laseName;}
    public Integer getAge() {return age;}


}
