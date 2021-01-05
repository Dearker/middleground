package com.hanyi.demo.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.hanyi.demo.entity.MinioUploadDto;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import io.minio.MinioClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @ClassName: middleground com.hanyi.demo.controller MinioController
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-06 22:09
 * @Version: 1.0
 */
@Slf4j
@Api(tags = "MinioController", value = "MinIO对象存储管理")
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Value("${minio.endPoint}")
    private String endPoint;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile file) {
        try {
            //创建一个MinIO的Java客户端
            MinioClient minioClient = new MinioClient(endPoint, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (isExist) {
                log.info("存储桶已经存在！");
            } else {
                //创建存储桶并设置只读权限
                minioClient.makeBucket(bucketName);
                minioClient.setBucketPolicy(bucketName, "*.*");
            }
            String filename = file.getOriginalFilename();
            // 设置存储对象名称
            String objectName = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + "/" + filename;
            // 使用putObject上传一个文件到存储桶中
            minioClient.putObject(bucketName, objectName, file.getInputStream(), file.getContentType());
            log.info("文件上传成功!");
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(filename);
            minioUploadDto.setUrl(endPoint + "/" + bucketName + "/" + objectName);
            return ResponseResult.success(minioUploadDto);
        } catch (Exception e) {
            log.info("上传发生错误: {}！", e.getMessage());
        }
        return ResponseResult.failure(ResultCode.UPLOAD_FAILED);
    }

    @ApiOperation("文件删除")
    @PostMapping("/delete")
    public ResponseResult delete(@RequestParam("objectName") String objectName) {
        try {
            MinioClient minioClient = new MinioClient(endPoint, accessKey, secretKey);
            minioClient.removeObject(bucketName, objectName);
            return ResponseResult.success("删除成功");
        } catch (Exception e) {
            log.error("异常", e);
        }
        return ResponseResult.failure(ResultCode.FAILED_TO_DELETE);
    }

}
