package com.hanyi.web;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件测试类
 * </p>
 *
 * @author wenchangwei
 * @since 4:56 下午 2021/1/2
 */
public class FileTest {

    /**
     * Files不能使用测试类获取文件路径
     *
     * @param args 测试
     * @throws IOException 异常
     */
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("apply.txt");
        System.out.println(path.toAbsolutePath());
        System.out.println(path.toUri());

        read(path.toAbsolutePath().toString());
        readTxt(path.toAbsolutePath().toString());

        List<String> stringList = Files.readAllLines(path);

        stringList.forEach(System.out::println);
        List<String> strings = Files.lines(path).collect(Collectors.toList());
        strings.forEach(System.out::println);
    }


    private static void read(String file) {
        // 创建字符流对象，并根据已创建的字节流对象创建字符流对象
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader raf = new BufferedReader(isr)) {

            String s;
            // 读取文件内容，并将其打印
            while ((s = raf.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readTxt(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                 BufferedReader br = new BufferedReader(isr)) {

                String lineTxt;
                while ((lineTxt = br.readLine()) != null) {
                    System.out.println(lineTxt);
                }
            } catch (IOException e) {
                System.out.println("文件读取错误!");
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}

