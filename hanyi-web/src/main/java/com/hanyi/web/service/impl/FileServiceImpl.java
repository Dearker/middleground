package com.hanyi.web.service.impl;

import com.hanyi.web.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 文件逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 11:12 下午 2021/1/2
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    /**
     * 通过老方式读取文件
     *
     * @param fileName 文件路径
     * @return 返回读取的结果
     */
    @Override
    public List<String> readFileByOld(String fileName) {
        List<String> stringList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader raf = new BufferedReader(isr)) {

            String s;
            // 读取文件内容，并将其打印
            while ((s = raf.readLine()) != null) {
                stringList.add(s);
            }
        } catch (IOException e) {
            log.error("异常情况", e);
        }
        return stringList;
    }

    /**
     * 根据files方式读取文件
     *
     * @param fileName 文件名称
     * @return 返回读取的结果
     */
    @Override
    public List<String> readAllLinesByFiles(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            log.error(e.toString());
        }
        return Collections.emptyList();
    }

    /**
     * 使用files方式逐行读取文件
     *
     * @param fileName 文件名称
     * @return 返回读取的结果
     */
    @Override
    public List<String> readLineByFiles(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            log.error("异常", e);
        }
        return Collections.emptyList();
    }

    /**
     * 根据文件名称创建文件，并写入内容
     *
     * @param fileName 文件名称
     * @return 返回文件内容
     */
    @Override
    public List<String> createFile(String fileName) {

        Path path = Paths.get(fileName);

        if (path.toFile().exists()) {
            List<String> stringList = Arrays.asList("哈士奇", "柯基", "柴犬");
            try {
                //在文件后面进行追加内容
                Files.write(path, stringList, StandardOpenOption.APPEND);
            } catch (IOException e) {
                log.error("异常", e);
            }
        } else {
            List<String> stringList = Arrays.asList("哈哈哈", "看看看", "奥术大师");
            try {
                Files.createFile(path);
                Files.write(path, stringList);
            } catch (IOException e) {
                log.error("异常", e);
            }
        }

        try (Stream<String> stream = Files.lines(path)) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            log.error("异常", e);
        }

        return Collections.emptyList();
    }

}
