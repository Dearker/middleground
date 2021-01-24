package com.hanyi.web.common.download.ext;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:02 上午 2021/1/23
 */
public interface DownloadProgressMonitor {

    /**
     * 计算下载进度
     *
     * @param contentLength 文件总大小
     */
    void calculateDownloadProgress(long contentLength);

    /**
     * 已下载的字节数
     *
     * @return 返回已下载的字节数
     */
    long getAlreadyDownloadLength();

}
