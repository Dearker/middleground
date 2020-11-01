package com.hanyi.hikari.controller;

import cn.hutool.core.collection.CollUtil;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import com.hanyi.hikari.pojo.CategoryEntity;
import com.hanyi.hikari.request.CategoryQueryParam;
import com.hanyi.hikari.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:05 下午 2020/10/27
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    /**
     * 分类逻辑层
     */
    @Resource
    private CategoryService categoryService;

    @GetMapping("/tree")
    public ResponseResult tree() {
        return ResponseResult.success(categoryService.listWithTree());
    }

    @PostMapping("/delete")
    public ResponseResult delete(@RequestBody List<Long> categoryIdList) {
        if (CollUtil.isNotEmpty(categoryIdList)) {
            return ResponseResult.failure(ResultCode.PARAM_IS_INVALID);
        }
        categoryService.removeMenuByIds(categoryIdList);
        return ResponseResult.success();
    }

    /**
     * 根据条件查询分类集合
     *
     * @param categoryQueryParam 分类查询参数
     * @return 返回分类集合
     */
    @PostMapping("/findPageByCondition")
    public ResponseResult findPageByCondition(@RequestBody CategoryQueryParam categoryQueryParam) {
        return ResponseResult.success(categoryService.findPageByCondition(categoryQueryParam));
    }

    /**
     * 根据id更新分类信息
     *
     * @param categoryEntity 分类对象
     * @return 返回更新结果
     */
    @PostMapping("/update")
    public ResponseResult updateCategoryById(@RequestBody CategoryEntity categoryEntity) {
        categoryService.updateCategoryById(categoryEntity);
        return ResponseResult.success();
    }

}
