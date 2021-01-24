package com.hanyi.web.common.download.ext;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:17 上午 2021/1/23
 */
public interface Downloader {

    /**
     * 下载文件
     *
     * @param fileUrl 文件地址
     * @param dir     目录名称
     * @throws IOException 异常
     */
    void download(String fileUrl, String dir) throws IOException;

}
