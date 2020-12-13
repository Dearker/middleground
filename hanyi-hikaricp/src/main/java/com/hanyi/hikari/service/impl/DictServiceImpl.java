package com.hanyi.hikari.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.hikari.dao.DictDao;
import com.hanyi.hikari.pojo.DictEntity;
import com.hanyi.hikari.service.DictService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 9:50 下午 2020/12/11
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictDao, DictEntity> implements DictService {

    /**
     * 递归获取字典层级集合
     *
     * @return 返回集合
     */
    @Override
    public List<DictEntity> findAllByRecursion() {

        LambdaQueryWrapper<DictEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictEntity::getOpenFlag, "1");

        List<DictEntity> dictEntityList = baseMapper.selectList(queryWrapper);

        dictEntityList.forEach(s -> {
            if (Objects.isNull(s.getParentId())) {
                s.setParentId(0);
            }
        });

        return dictEntityList.stream().filter(s -> s.getParentId().equals(0))
                .peek(menu -> menu.setDictEntityList(this.getChildren(menu, dictEntityList)))
                .collect(Collectors.toList());
    }

    /**
     * 递归查找所有菜单的子菜单
     *
     * @param root 根节点
     * @param all  所有集合对象
     * @return 返回菜单集合
     */
    private List<DictEntity> getChildren(DictEntity root, List<DictEntity> all) {
        return all.stream().filter(dictEntity ->
                dictEntity.getParentId().equals(root.getChildrenId())).peek(categoryEntity ->
                categoryEntity.setDictEntityList(this.getChildren(categoryEntity, all)))
                .collect(Collectors.toList());
    }

}
