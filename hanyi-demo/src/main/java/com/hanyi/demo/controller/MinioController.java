package com.hanyi.demo.controller;

import com.hanyi.demo.entity.MinioUploadDto;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import io.minio.MinioClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: middleground com.hanyi.demo.controller MinioController
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-06 22:09
 * @Version: 1.0
 */
@Api(tags = "MinioController", description = "MinIO对象存储管理")
@RestController
@RequestMapping("/minio")
public class MinioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);

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
                LOGGER.info("存储桶已经存在！");
            } else {
                //创建存储桶并设置只读权限
                minioClient.makeBucket(bucketName);
                minioClient.setBucketPolicy(bucketName, "*.*");
            }
            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 设置存储对象名称
            String objectName = sdf.format(new Date()) + "/" + filename;
            // 使用putObject上传一个文件到存储桶中
            minioClient.putObject(bucketName, objectName, file.getInputStream(), file.getContentType());
            LOGGER.info("文件上传成功!");
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(filename);
            minioUploadDto.setUrl(endPoint + "/" + bucketName + "/" + objectName);
            return ResponseResult.success(minioUploadDto);
        } catch (Exception e) {
            LOGGER.info("上传发生错误: {}！", e.getMessage());
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
            e.printStackTrace();
        }
        return ResponseResult.failure(ResultCode.FAILED_TO_DELETE);
    }

}
