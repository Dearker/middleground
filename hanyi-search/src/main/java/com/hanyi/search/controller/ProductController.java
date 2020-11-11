package com.hanyi.search.controller;

import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import com.hanyi.search.bo.SkuEsModel;
import com.hanyi.search.request.SearchParam;
import com.hanyi.search.service.ProductSaveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品控制层
 * </p>
 *
 * @author wenchangwei
 * @since 11:59 上午 2020/11/1
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductSaveService productSaveService;

    /**
     * 上传到es
     *
     * @param esModels 模型集合
     * @return 是否存在错误
     */
    @PostMapping("/up")
    public ResponseResult productStatusUp(@RequestBody List<SkuEsModel> esModels) {
        try {
            return ResponseResult.success(productSaveService.productStatusUp(esModels));
        } catch (IOException e) {
            return ResponseResult.failure(ResultCode.FAILED);
        }
    }

    /**
     * 根据查询条件查询数据
     *
     * @param param 参数参数
     * @return 返回查询结果
     */
    @PostMapping("/search")
    public ResponseResult search(@RequestBody SearchParam param) {
        return ResponseResult.success(productSaveService.search(param));
    }

}
