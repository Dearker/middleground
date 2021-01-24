package com.hanyi.web.common.download.ext;

import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.web.common.download.support.DownloadProgressPrinter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:01 上午 2021/1/23
 */
@Slf4j
public abstract class AbstractDownloadProgressMonitorResponseExtractor<T> implements ResponseExtractor<T>, DownloadProgressMonitor {

    protected DownloadProgressPrinter downloadProgressPrinter;

    public AbstractDownloadProgressMonitorResponseExtractor(DownloadProgressPrinter downloadProgressPrinter) {
        this.downloadProgressPrinter = downloadProgressPrinter;
    }

    public AbstractDownloadProgressMonitorResponseExtractor() {
        this.downloadProgressPrinter = DownloadProgressPrinter.defaultDownloadProgressPrinter();
    }

    @Override
    public T extractData(ClientHttpResponse response) throws IOException {
        long contentLength = response.getHeaders().getContentLength();
        this.calculateDownloadProgress(contentLength);
        return this.doExtractData(response);
    }

    protected abstract T doExtractData(ClientHttpResponse response) throws IOException;


    @Override
    public void calculateDownloadProgress(long contentLength) {
        long totalSize = contentLength / 1024;
        String task = getTask();
        CompletableFuture.runAsync(() -> {
            long speed;
            long tmp = 0;
            while (contentLength - tmp > 0) {
                speed = getAlreadyDownloadLength() - tmp;
                tmp = getAlreadyDownloadLength();
                downloadProgressPrinter.print(task, totalSize, tmp, speed);
                sleep();
            }
        }).exceptionally(e -> {
            log.error("", e);
            return null;
        });
    }

    protected String getTask() {
        return Thread.currentThread().getName();
    }

    private void sleep() {
        ThreadUtil.sleep(1000);
    }

}
