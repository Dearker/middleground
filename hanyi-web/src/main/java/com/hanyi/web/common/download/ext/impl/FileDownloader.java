package com.hanyi.web.common.download.ext.impl;

import com.hanyi.web.common.download.ext.AbstractDownloader;
import com.hanyi.web.common.download.support.DownloadProgressPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 单线程下载
 * </p>
 *
 * @author wenchangwei
 * @since 11:47 上午 2021/1/23
 */
public class FileDownloader extends AbstractDownloader {

    public FileDownloader(DownloadProgressPrinter downloadProgressPrinter) {
        super(downloadProgressPrinter);
    }

    public FileDownloader() {
        super(DownloadProgressPrinter.defaultDownloadProgressPrinter());
    }

    @Override
    protected void doDownload(String fileUrl, String dir, String fileName, HttpHeaders headers) throws IOException {
        String filePath = dir + File.separator + fileName;
        //创建临时下载文件
        FileResponseExtractor extractor = new FileResponseExtractor(filePath + ".download", downloadProgressPrinter);
        File tmpFile = restTemplate.execute(fileUrl, HttpMethod.GET, null, extractor);
        //修改临时下载文件名称
        tmpFile.renameTo(new File(filePath));
    }
}
