package com.hanyi.daily.mapper.one;

import com.hanyi.daily.pojo.TimeInfo;

import java.util.List;

/**
 * <p>
 * 时间详情数据层
 * </p>
 *
 * @author wenchangwei
 * @since 8:12 下午 2020/10/1
 */
public interface TimeInfoMapper {

    /**
     * 新增时间
     *
     * @param timeInfo 时间对象
     * @return 返回新增条数
     */
    Integer insert(TimeInfo timeInfo);

    /**
     * 查询所有时间对象
     *
     * @return 返回时间集合
     */
    List<TimeInfo> findAll();

    /**
     * 根据id查询时间详情
     *
     * @param id 详情id
     * @return 返回时间对象
     */
    TimeInfo findById(Long id);
}
