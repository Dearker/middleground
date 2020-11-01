package com.hanyi.search.service;


import com.hanyi.search.bo.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品保存服务
 * </p>
 *
 * @author wenchangwei
 * @since 11:50 上午 2020/11/1
 */
public interface ProductSaveService {

    /**
     * 上传到es
     * @param esModels 模型集合
     * @return true 有错误 false 无错误
     * @throws IOException 异常
     */
    Boolean productStatusUp(List<SkuEsModel> esModels) throws IOException;
}
