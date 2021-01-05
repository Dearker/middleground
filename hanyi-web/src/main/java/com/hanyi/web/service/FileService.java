package com.hanyi.web.service;

import java.util.List;

/**
 * <p>
 * 文件接口层
 * </p>
 *
 * @author wenchangwei
 * @since 11:11 下午 2021/1/2
 */
public interface FileService {

    /**
     * 通过老方式读取文件
     *
     * @param fileName 文件路径
     * @return 返回读取的结果
     */
    List<String> readFileByOld(String fileName);

    /**
     * 使用files方式一次性读取全部文件
     *
     * @param fileName 文件名称
     * @return 返回读取的结果
     */
    List<String> readAllLinesByFiles(String fileName);

    /**
     * 使用files方式逐行读取文件
     *
     * @param fileName 文件名称
     * @return 返回读取的结果
     */
    List<String> readLineByFiles(String fileName);

    /**
     * 根据文件名称创建文件，并写入内容
     *
     * @param fileName 文件名称
     * @return 返回文件内容
     */
    List<String> createFile(String fileName);

}
