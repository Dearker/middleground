package com.hanyi.daily.service;

import cn.hutool.core.lang.Snowflake;
import com.hanyi.daily.mapper.one.TimeInfoMapper;
import com.hanyi.daily.pojo.TimeInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 时间详情逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 8:23 下午 2020/10/1
 */
@Service
public class TimeInfoService {

    /**
     * 雪花对象
     */
    @Resource
    private Snowflake snowflake;

    /**
     * 时间数据层
     */
    @Resource
    private TimeInfoMapper timeInfoMapper;

    /**
     * 新增时间
     *
     * @param timeInfo 时间对象
     * @return 返回新增条数
     */
    public Integer insert(TimeInfo timeInfo) {
        timeInfo.setId(snowflake.nextId());
        timeInfo.setTimeExtent(System.currentTimeMillis());
        timeInfo.setCreateTime(LocalDateTime.now());
        return timeInfoMapper.insert(timeInfo);
    }

    /**
     * 查询所有的时间集合
     *
     * @return 返回集合
     */
    public List<TimeInfo> findAll() {
        return timeInfoMapper.findAll();
    }

}
