package com.hanyi.web;

import com.hanyi.web.common.download.ext.Downloader;
import com.hanyi.web.common.download.ext.impl.FileDownloader;
import com.hanyi.web.common.download.ext.impl.MultiThreadFileDownloader;
import org.junit.Test;

import java.io.IOException;

/**
 * <p>
 * 文件下载测试类
 * </p>
 *
 * @author wenchangwei
 * @since 11:46 上午 2021/1/23
 */
public class DownloadTest {

    /**
     * 单线程下载
     *
     * @throws IOException 异常
     */
    @Test
    public void fileDownload() throws IOException {
        String fileUrl = "https://download.jetbrains.8686c.com/idea/ideaIU-2020.3.dmg";
        Downloader downloader = new FileDownloader();
        downloader.download(fileUrl, "/Volumes/husky/temp");
    }

    /**
     * 多线程下载文件
     *
     * @throws IOException 异常
     */
    @Test
    public void multiThreadDownload() throws IOException {
        String fileUrl = "https://download.jetbrains.8686c.com/idea/ideaIU-2020.3.dmg";
        Downloader downloader = new MultiThreadFileDownloader(50);
        downloader.download(fileUrl, "/Volumes/husky/temp");
    }

}
