package com.highway.study.testjava;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * @author JH
 * @date 2017/8/2
 */


public class FileDemo {

    public static void main(String[] args) {
//        read();
        basicFileOut();
    }

    private static void read() {
        File file = new File("text.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line + "\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BufferedWriter writer = null;
        try {                                                                  // 在后面追加内容
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));

            writer.append("\n second line add \n thrid line add");
//            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 从磁盘中读入文件
     * InputStreamReader可以把InputStream转换为Reader
     * OutputStreamWriter可以把OutputStream转换为Writer
     * 主要为了国际化，处理16位的Unicode字符
     *
     * @param filename
     * @return
     */
    public static String read(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
//            BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String s;
            StringBuilder builder = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                builder.append(s + "\n");
            }
            reader.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 缓冲输入文件
     */

    public static void readBufferFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("text.txt"));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从内存输入
     */
    public static void memoryInput() {
        StringReader reader = new StringReader(read("text.txt"));
        int c;
        try {
            while ((c = reader.read()) != -1) {
                System.out.println((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件输出
     */
    public static void basicFileOut() {
        String name = "text2.txt";
        BufferedWriter writer = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("G:\\DailyStudy\\app\\src\\main\\java\\com\\highway\\study\\testjava\\FileDemo.java"));
            writer = new BufferedWriter(new FileWriter(name));
            String s;
            while ((s = reader.readLine()) != null) {
                writer.write(s, 0, s.length());
                writer.newLine();
            }
            writer.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取二进制文件
     */
    public static void readBinary(String filename) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
