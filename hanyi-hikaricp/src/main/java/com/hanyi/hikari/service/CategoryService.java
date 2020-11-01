package com.hanyi.hikari.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.hikari.pojo.CategoryEntity;
import com.hanyi.hikari.request.CategoryQueryParam;

import java.util.List;

/**
 * <p>
 * 商品三级分类服务层
 * </p>
 *
 * @author wenchangwei
 * @since 8:56 下午 2020/10/27
 */
public interface CategoryService extends IService<CategoryEntity> {

    /**
     * 获取父子结构集合
     *
     * @return 返回集合
     */
    List<CategoryEntity> listWithTree();

    /**
     * 批量删除分类集合
     *
     * @param categoryIdList 分类id集合
     */
    void removeMenuByIds(List<Long> categoryIdList);

    /**
     * 根据条件查询分类集合
     *
     * @param categoryQueryParam 分类查询参数
     * @return 返回分类集合
     */
    IPage<CategoryEntity> findPageByCondition(CategoryQueryParam categoryQueryParam);

    /**
     * 根据id更新分类信息
     *
     * @param categoryEntity 分类对象
     */
    void updateCategoryById(CategoryEntity categoryEntity);
}
