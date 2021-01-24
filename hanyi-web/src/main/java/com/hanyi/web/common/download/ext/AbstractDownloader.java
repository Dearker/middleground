package com.hanyi.web.common.download.ext;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.web.common.download.support.DownloadProgressPrinter;
import com.hanyi.web.common.util.RestTemplateBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:18 上午 2021/1/23
 */
@Slf4j
public abstract class AbstractDownloader implements Downloader {

    protected RestTemplate restTemplate;
    protected DownloadProgressPrinter downloadProgressPrinter;

    public AbstractDownloader(DownloadProgressPrinter downloadProgressPrinter) {
        this.restTemplate = RestTemplateBuilder.builder().build();
        this.downloadProgressPrinter = downloadProgressPrinter;
    }

    @Override
    public void download(String fileUrl, String dir) throws IOException {
        long start = System.currentTimeMillis();

        String decodeFileUrl = URLDecoder.decode(fileUrl, CharsetUtil.UTF_8);

        //通过Http协议的Head方法获取到文件的总大小
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> entity = restTemplate.exchange(decodeFileUrl, HttpMethod.HEAD, requestEntity, String.class);
        String fileName = this.getFileName(decodeFileUrl, entity.getHeaders());

        doDownload(decodeFileUrl, dir, fileName, entity.getHeaders());

        log.info("总共下载文件耗时:" + (System.currentTimeMillis() - start) / 1000 + "s");
    }

    protected abstract void doDownload(String decodeFileUrl, String dir, String fileName, HttpHeaders headers) throws IOException;

    /**
     * 获取文件的名称
     *
     * @param fileUrl 文件路径
     * @return 返回文件名称
     */
    private String getFileName(String fileUrl, HttpHeaders headers) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(StrUtil.SLASH) + 1);
        if (fileName.contains(StrUtil.DOT)) {
            String suffix = fileName.substring(fileName.lastIndexOf(StrUtil.DOT) + 1);
            if (suffix.length() > 4 || suffix.contains("?")) {
                fileName = getFileNameFromHeader(headers);
            }
        } else {
            fileName = getFileNameFromHeader(headers);
        }
        return fileName;
    }

    private String getFileNameFromHeader(HttpHeaders headers) {
        String fileName = headers.getContentDisposition().getFilename();
        if (StringUtils.isEmpty(fileName)) {
            return UUID.randomUUID().toString();
        }
        return fileName;
    }

}
