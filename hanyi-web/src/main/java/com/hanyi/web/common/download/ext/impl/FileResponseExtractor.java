package com.hanyi.web.common.download.ext.impl;

import com.hanyi.web.common.download.ext.AbstractDownloadProgressMonitorResponseExtractor;
import com.hanyi.web.common.download.support.DownloadProgressPrinter;
import org.springframework.http.client.ClientHttpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:07 上午 2021/1/23
 */
public class FileResponseExtractor extends AbstractDownloadProgressMonitorResponseExtractor<File> {

    /**
     * 已下载的字节数
     */
    private long byteCount;

    /**
     * 文件的路径
     */
    private final String filePath;

    /**
     * 多任务下载时用
     */
    private final int index;

    public FileResponseExtractor(int index, String filePath, DownloadProgressPrinter downloadProgressPrinter) {
        super(downloadProgressPrinter);
        this.index = index;
        this.filePath = filePath;
    }

    public FileResponseExtractor(String filePath, DownloadProgressPrinter downloadProgressPrinter) {
        this(0, filePath, downloadProgressPrinter);
    }

    @Override
    protected File doExtractData(ClientHttpResponse response) throws IOException {
        InputStream in = response.getBody();
        File file = new File(filePath);

        try (FileOutputStream out = new FileOutputStream(file)) {
            int bytesRead;
            for (byte[] buffer = new byte[4096]; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } catch (Exception ignore) {

        }
        return file;
    }

    /**
     * 已下载的字节数
     *
     * @return 返回已下载的字节数
     */
    @Override
    public long getAlreadyDownloadLength() {
        return byteCount;
    }

    public int getIndex() {
        return index;
    }

    @Override
    protected String getTask() {
        return String.valueOf(this.getIndex());
    }
}
