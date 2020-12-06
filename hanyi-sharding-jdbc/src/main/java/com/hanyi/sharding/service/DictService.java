package com.hanyi.sharding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.sharding.pojo.Dict;

import java.util.List;

/**
 * <p>
 * 字典接口层
 * </p>
 *
 * @author wenchangwei
 * @since 8:46 下午 2020/12/3
 */
public interface DictService extends IService<Dict> {

    /**
     * 新增字典
     *
     * @param dict 字典对象
     * @return 返回新增条数
     */
    int insert(Dict dict);

    /**
     * 根据id删除字典信息
     *
     * @param id 主键id
     * @return 返回删除条数
     */
    int deleteById(Long id);

    /**
     * 查询所有数据
     *
     * @return 返回所有数据
     */
    List<Dict> findAll();
}
