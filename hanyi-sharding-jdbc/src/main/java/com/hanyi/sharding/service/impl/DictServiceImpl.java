package com.hanyi.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.sharding.dao.DictMapper;
import com.hanyi.sharding.pojo.Dict;
import com.hanyi.sharding.service.DictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 8:47 下午 2020/12/3
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * 新增字典
     *
     * @param dict 字典对象
     * @return 返回新增条数
     */
    @Override
    public int insert(Dict dict) {
        return baseMapper.insert(dict);
    }

    /**
     * 根据id删除字典信息
     *
     * @param id 主键id
     * @return 返回删除条数
     */
    @Override
    public int deleteById(Long id) {
        return baseMapper.deleteById(id);
    }

    /**
     * 查询所有数据
     *
     * @return 返回所有数据
     */
    @Override
    public List<Dict> findAll() {
        return baseMapper.selectList(null);
    }
}
