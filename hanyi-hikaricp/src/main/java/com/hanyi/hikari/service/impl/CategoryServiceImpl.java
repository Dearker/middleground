package com.hanyi.hikari.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.hikari.dao.CategoryDao;
import com.hanyi.hikari.pojo.CategoryEntity;
import com.hanyi.hikari.request.CategoryQueryParam;
import com.hanyi.hikari.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 8:57 下午 2020/10/27
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    /**
     * 分类数据层
     */
    @Resource
    private CategoryDao categoryDao;

    /**
     * 获取父子结构集合
     *
     * @return 返回集合
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //2、找到所有的一级分类
        return entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).peek(menu -> menu.setChildren(this.getChildren(menu, entities)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 :
                        menu.getSort()))).collect(Collectors.toList());
    }

    /**
     * 批量删除分类集合
     *
     * @param categoryIdList 分类id集合
     */
    @Override
    public void removeMenuByIds(List<Long> categoryIdList) {
        //逻辑删除
        baseMapper.deleteBatchIds(categoryIdList);
    }

    @Override
    public IPage<CategoryEntity> findPageByCondition(CategoryQueryParam categoryQueryParam) {

        long currentPage = Objects.isNull(categoryQueryParam.getCurrentPage()) ? 1 : categoryQueryParam.getCurrentPage();
        long pageSize = Objects.isNull(categoryQueryParam.getPageSize()) ? 10 : categoryQueryParam.getPageSize();

        //分页对象
        Page<CategoryEntity> page = new Page<>(currentPage, pageSize);
        QueryWrapper<CategoryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_cid", categoryQueryParam.getParentCid())
                .and(obj -> obj.like("name", categoryQueryParam.getName()).or()
                        .eq("cat_level", categoryQueryParam.getCatLevel()));
        return this.page(page, queryWrapper);
    }

    /**
     * 根据id更新分类信息
     *
     * @param categoryEntity 分类对象
     */
    @Override
    public void updateCategoryById(CategoryEntity categoryEntity) {
        this.updateById(categoryEntity);

        CategoryEntity category = new CategoryEntity();
        category.setProductUnit("柯基");
        UpdateWrapper<CategoryEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("cat_id", categoryEntity.getCatId());

        this.update(category, updateWrapper);
    }

    /**
     * 递归查找所有菜单的子菜单
     *
     * @param root 根节点
     * @param all  所有集合对象
     * @return 返回菜单集合
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        return all.stream().filter(categoryEntity ->
                categoryEntity.getParentCid().equals(root.getCatId())).peek(categoryEntity ->
                categoryEntity.setChildren(this.getChildren(categoryEntity, all)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 :
                        menu.getSort()))).collect(Collectors.toList());
    }

}
