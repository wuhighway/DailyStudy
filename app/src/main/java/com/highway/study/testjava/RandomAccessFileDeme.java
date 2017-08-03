package com.highway.study.testjava;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author JH
 * @date 2017/8/3
 */


public class RandomAccessFileDeme {

    static String file = "rtext.txt";
    static void display() {
        try {
            RandomAccessFile rf = new RandomAccessFile(file, "r");
            for (int i = 0; i < 7; i++) {
                System.out.println("Value " + i + ":" + rf.readDouble());
            }
            System.out.println(rf.readUTF());
            rf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            RandomAccessFile rf = new RandomAccessFile(file, "rw");
            for (int i = 0; i < 7; i++) {
                rf.writeDouble(i * 1.14);
            }
            rf.writeUTF("utf text");
            rf.close();
            display();
            rf = new RandomAccessFile(file, "rw");
            rf.seek(5 * 8);
            rf.writeDouble(47.00111);
            rf.close();
            display();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
