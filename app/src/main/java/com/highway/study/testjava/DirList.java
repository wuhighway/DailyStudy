package com.highway.study.testjava;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 过滤指定的文件名
 * @author JH
 * @date 2017/8/3
 */


public class DirList {
    public static FilenameFilter filter(final String regex){
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            @Override
            public boolean accept(File dir, String name) {
//                System.out.println(regex);
//                System.out.println(name);
                return pattern.matcher(name).matches();
            }
        };
    }

    public static void main(String[] args) {
        File file = new File(".");
        String[] list;
        list = file.list(filter("text.txt"));
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String name : list) {
            System.out.println(name);
        }
    }
}
