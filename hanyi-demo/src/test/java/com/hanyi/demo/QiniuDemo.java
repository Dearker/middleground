package com.hanyi.demo;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @ClassName: middleground com.hanyi.demo.QiniuDemo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-10 11:28
 * @Version: 1.0
 */
public class QiniuDemo {

    /**
     * 将图片上传到七牛云服务
     *      1.更新用户图片信息（用户id=key）
     *      2.访问图片
     *          存储空间分配的：http://pkbivgfrm.bkt.clouddn.com
     *          上传的文件名
     *          更新图片之后：访问的时候，再请求连接添加上时间戳
     *
     */
    @Test
    public void testUpload01() {
        //构造一个带指定Zone对象的配置类

        //指定上传文件服务器地址：
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        //上传管理器
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "yLi_wffnpXN8XiTMy5FZCOOblsjJK6W0sNtA-EWZ";
        String secretKey = "VDPDFKlDR9xeVcF1F1_24YbGfn7weXgcJ3dFhqcR";
        String bucket = "hanyi2";

        //图片路径
        String localFilePath = "/Volumes/husky/Git/seata/seata-samples/doc/img/seata.png";

        //存入到存储空间的文件名
        String key = "test";

        //身份认证
        Auth auth = Auth.create(accessKey, secretKey);
        //指定覆盖上传
        String upToken = auth.uploadToken(bucket,key);
        try {
            //上传
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    //断点续传
    @Test
    public void testUpload02() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        //...生成上传凭证，然后准备上传
        String accessKey = "yLi_wffnpXN8XiTMy5FZCOOblsjJK6W0sNtA-EWZ";
        String secretKey = "VDPDFKlDR9xeVcF1F1_24YbGfn7weXgcJ3dFhqcR";
        String bucket = "hanyi2";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/Volumes/husky/Git/seata/seata-samples/doc/img/fescar.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "testExcel";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        //断点续传：
        String localTempDir = Paths.get(System.getProperty("java.io.tmpdir"), bucket).toString();
        System.out.println(localTempDir);
        try {
            //设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            try {
                Response response = uploadManager.put(localFilePath, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
