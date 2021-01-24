package com.hanyi.web.common.download.support.impl;

import com.hanyi.web.common.download.support.DownloadProgressPrinter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:59 上午 2021/1/23
 */
@Slf4j
public class SimpleDownloadProgressPrinter implements DownloadProgressPrinter {

    private long contentLength;
    private long alreadyDownloadLength;

    /**
     * @param task                  下载任务名
     * @param contentLength         文件总大小
     * @param alreadyDownloadLength 已经下载的大小
     * @param speed                 下载速度
     */
    @Override
    public void print(String task, long contentLength, long alreadyDownloadLength, long speed) {
        this.contentLength = contentLength;
        this.alreadyDownloadLength = alreadyDownloadLength;
        log.info(task + " 文件总大小: " + contentLength + "KB, 已下载："
                + (alreadyDownloadLength / 1024) + "KB, 下载速度：" + (speed / 1000) + "KB");
    }

    @Override
    public long getContentLength() {
        return this.contentLength;
    }

    @Override
    public long getAlreadyDownloadLength() {
        return this.alreadyDownloadLength;
    }
}
