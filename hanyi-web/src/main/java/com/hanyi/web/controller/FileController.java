package com.hanyi.web.controller;

import com.hanyi.web.common.component.DynamicResourceComponent;
import com.hanyi.web.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 11:13 下午 2021/1/2
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    /**
     * 文件服务
     */
    private final FileService fileService;

    /**
     * 动态加载组件
     */
    private final DynamicResourceComponent dynamicResourceComponent;

    /**
     * 通过老方式读取文件
     *
     * @param fileName 文件路径
     * @return 返回读取的结果
     */
    @GetMapping("/old")
    public List<String> readFileByOld(String fileName) {
        return fileService.readFileByOld(fileName);
    }

    /**
     * 使用files方式一次性读取全部文件
     *
     * @param fileName 文件名称
     * @return 返回读取的结果
     */
    @GetMapping("/all")
    public List<String> readAllLinesByFiles(String fileName) {
        return fileService.readAllLinesByFiles(fileName);
    }

    /**
     * 使用files方式逐行读取文件
     *
     * @param fileName 文件名称
     * @return 返回读取的结果
     */
    @GetMapping("/line")
    public List<String> readLineByFiles(String fileName) {
        return fileService.readLineByFiles(fileName);
    }

    /**
     * 根据文件名称创建文件，并写入内容
     *
     * @param fileName 文件名称
     * @return 返回文件内容
     */
    @GetMapping("/create")
    public List<String> createFile(String fileName) {
        return fileService.createFile(fileName);
    }


    /**
     * 获取相对路径和属性集合
     *
     * @return 返回结果
     */
    @GetMapping("/props")
    public Map<String, Object> getFilePathAndProps() {
        return dynamicResourceComponent.getFilePathAndProps();
    }

}
