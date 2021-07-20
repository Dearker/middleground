package com.hanyi.clickhouse.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanyi.clickhouse.pojo.HitsTest;
import org.apache.ibatis.annotations.Param;

/**
 * 点击测试映射器
 *
 * @author weiwen
 * @date 2021/07/19
 */
public interface HitsTestMapper extends BaseMapper<HitsTest> {

    /**
     * 通过观察id删除
     *
     * @param watchId 看身份证
     */
    void deleteByWatchId(Integer watchId);

    /**
     * 更新标题通过观察id
     *
     * @param title   标题
     * @param watchId 看身份证
     */
    void updateTitleByWatchId(@Param("title") String title, @Param("watchId") Integer watchId);

}




