package com.hanyi.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 7:32 下午 2021/1/3
 */
public class WriteExample {

    /**
     * 耗时情况：
     * FileWriter 写入用时:152
     * BufferedWriter 写入用时:86
     * PrintWriterTest 写入用时:93
     * FileOutputStream 写入用时:109
     * BufferedOutputStream 写入用时:112
     * Files 写入用时:195
     * Files 缓存区写入用时:162
     *
     * @param args 参数
     * @throws IOException 异常
     */
    public static void main(String[] args) throws IOException {
        // 构建写入内容
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = new ArrayList<>(1000000);
        String contentStr = "ABCDEFGHIGKLMNOPQRSEUVWXYZ";
        for (int i = 0; i < 1000000; i++) {
            stringBuilder.append(contentStr);
            stringList.add(contentStr);
        }
        // 写入内容
        String content = stringBuilder.toString();
        // 存放文件的目录
        String filepath1 = "/Volumes/husky/Git/middleground-2.1.6/middleground/write1.txt";
        String filepath2 = "/Volumes/husky/Git/middleground-2.1.6/middleground/write2.txt";
        String filepath3 = "/Volumes/husky/Git/middleground-2.1.6/middleground/write3.txt";
        String filepath4 = "/Volumes/husky/Git/middleground-2.1.6/middleground/write4.txt";
        String filepath5 = "/Volumes/husky/Git/middleground-2.1.6/middleground/write5.txt";
        String filepath6 = "/Volumes/husky/Git/middleground-2.1.6/middleground/write6.txt";
        String filepath7 = "/Volumes/husky/Git/middleground-2.1.6/middleground/write7.txt";

        // 方法一:使用 FileWriter 写文件
        TimeInterval timer = DateUtil.timer();
        fileWriterTest(filepath1, content);
        System.out.println("FileWriter 写入用时:" + timer.intervalRestart());

        // 方法二:使用 BufferedWriter 写文件
        bufferedWriterTest(filepath2, content);
        System.out.println("BufferedWriter 写入用时:" + timer.intervalRestart());

        // 方法三:使用 PrintWriter 写文件
        printWriterTest(filepath3, content);
        System.out.println("PrintWriterTest 写入用时:" + timer.intervalRestart());

        // 方法四:使用 FileOutputStream  写文件
        fileOutputStreamTest(filepath4, content);
        System.out.println("FileOutputStream 写入用时:" + timer.intervalRestart());

        // 方法五:使用 BufferedOutputStream 写文件
        bufferedOutputStreamTest(filepath5, content);
        System.out.println("BufferedOutputStream 写入用时:" + timer.intervalRestart());

        // 方法六:使用 Files 写文件
        filesTest(filepath6, content);
        System.out.println("Files 写入用时:" + timer.intervalRestart());

        Files.write(Paths.get(filepath7), stringList);
        System.out.println("Files 缓存区写入用时:" + timer.intervalRestart());
    }

    /**
     * 方法六:使用 Files 写文件
     *
     * @param filepath 文件目录
     * @param content  待写入内容
     * @throws IOException 异常
     */
    private static void filesTest(String filepath, String content) throws IOException {
        Files.write(Paths.get(filepath), content.getBytes());
    }

    /**
     * 方法五:使用 BufferedOutputStream 写文件
     *
     * @param filepath 文件目录
     * @param content  待写入内容
     * @throws IOException 异常
     */
    private static void bufferedOutputStreamTest(String filepath, String content) throws IOException {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(filepath))) {
            bufferedOutputStream.write(content.getBytes());
        }
    }

    /**
     * 方法四:使用 FileOutputStream  写文件
     *
     * @param filepath 文件目录
     * @param content  待写入内容
     * @throws IOException 异常
     */
    private static void fileOutputStreamTest(String filepath, String content) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filepath)) {
            byte[] bytes = content.getBytes();
            fileOutputStream.write(bytes);
        }
    }

    /**
     * 方法三:使用 PrintWriter 写文件
     *
     * @param filepath 文件目录
     * @param content  待写入内容
     * @throws IOException 异常
     */
    private static void printWriterTest(String filepath, String content) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filepath))) {
            printWriter.print(content);
        }
    }

    /**
     * 方法二:使用 BufferedWriter 写文件
     *
     * @param filepath 文件目录
     * @param content  待写入内容
     * @throws IOException 异常
     */
    private static void bufferedWriterTest(String filepath, String content) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            bufferedWriter.write(content);
        }
    }

    /**
     * 方法一:使用 FileWriter 写文件
     *
     * @param filepath 文件目录
     * @param content  待写入内容
     * @throws IOException 异常
     */
    private static void fileWriterTest(String filepath, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.append(content);
        }
    }

}
