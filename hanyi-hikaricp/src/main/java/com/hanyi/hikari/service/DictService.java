package com.hanyi.hikari.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.hikari.pojo.DictEntity;

import java.util.List;

/**
 * <p>
 * 字典接口层
 * </p>
 *
 * @author wenchangwei
 * @since 9:49 下午 2020/12/11
 */
public interface DictService extends IService<DictEntity> {

    /**
     * 递归获取字典层级集合
     *
     * @return 返回集合
     */
    List<DictEntity> findAllByRecursion();

}
