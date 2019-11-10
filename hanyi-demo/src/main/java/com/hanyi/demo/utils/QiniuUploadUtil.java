package com.hanyi.demo.utils;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @ClassName: middleground com.hanyi.demo.utils QiniuUploadUtil
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-10 11:28
 * @Version: 1.0
 */
public class QiniuUploadUtil {

    private static final String ACCESS_KEY = "yLi_wffnpXN8XiTMy5FZCOOblsjJK6W0sNtA-EWZ";
    private static final String SECRET_KEY = "VDPDFKlDR9xeVcF1F1_24YbGfn7weXgcJ3dFhqcR";
    private static final String BUCKET = "hanyi2";
    private static final String PRIX = "http://q0ft9mlpn.bkt.clouddn.com/";
    private UploadManager manager;

    public QiniuUploadUtil() {
        //初始化基本配置
        Configuration cfg = new Configuration(Zone.zone0());
        //创建上传管理器
        manager = new UploadManager(cfg);
    }

    public String upload(String imgName, byte[] bytes) {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //构造覆盖上传token
        String upToken = auth.uploadToken(BUCKET, imgName);
        try {
            Response response = manager.put(bytes, imgName, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
                    DefaultPutRet.class);
            //返回请求地址
            return PRIX + putRet.key + "?t=" + System.currentTimeMillis();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
