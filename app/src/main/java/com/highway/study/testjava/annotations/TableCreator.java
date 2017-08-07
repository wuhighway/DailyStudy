package com.highway.study.testjava.annotations;


import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JH
 * @date 2017/8/7
 */


public class TableCreator {

    public static void main(String[] args) {
        Member member = new Member();
        String[] classNames = {member.getClass().getName()};

        for (String className : classNames) {
            try {
                Class clazz = Class.forName(className);
                DBTable dbTable = (DBTable) clazz.getAnnotation(DBTable.class);
                if (dbTable == null) {
                    System.out.println("no dbtable class");
                    return;
                }
                String tableName = dbTable.name();
                if (tableName.length() < 1)
                    tableName = clazz.getName().toUpperCase();
                List<String> columnDefs = new ArrayList<>();
                for (Field field : clazz.getDeclaredFields()) {
                    String columnName = null;
                    Annotation[] anns = field.getDeclaredAnnotations();
                    if (anns.length < 1) {
                        continue;
                    }
                    if (anns[0] instanceof SQLInteger) {
                        SQLInteger integer = (SQLInteger) anns[0];
                        if (integer.name().length() < 1)
                            columnName = field.getName().toUpperCase();
                        else
                            columnName = integer.name();
                        columnDefs.add(columnName + " INT" + getConstraints(integer.constraints()));
                    }

                    if (anns[0] instanceof SQLString) {
                        SQLString string = (SQLString) anns[0];
                        if (string.name().length() < 1)
                            columnName = field.getName().toUpperCase();
                        else
                            columnName = string.name();
                        columnDefs.add(columnName + " VARCHAR(" + string.value() + ")" + getConstraints(string.constraints()));
                    }
                    StringBuilder createCommand  = new StringBuilder("CREATE TABLE " + tableName + "(");
                    for (String columndef : columnDefs) {
                        createCommand.append("\n     " + columndef + ",");
                    }
                    String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ")";
                    System.out.println(tableCreate.toString());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull()) {
            constraints += " NOT NULL";
        }
        if (con.primaryKey()) {
            constraints += " PRIMARY KEY";
        }
        if (con.unique()) {
            constraints += " UNIQUE";
        }
        return constraints;
    }
}
