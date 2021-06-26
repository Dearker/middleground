package com.hanyi.dynamic.common.component;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hanyi.dynamic.dao.DataBaseConfigMapper;
import com.hanyi.dynamic.pojo.DataBaseConfigInfo;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 动态切换组件
 * </p>
 *
 * @author wenchangwei
 * @since 2021/6/26 5:54 下午
 */
@Component
@RequiredArgsConstructor
public class DynamicChangeComponent {

    private Map<Long, DataSource> initDataSourceMap = null;

    private final DataSource dataSource;

    /**
     * 数据源配置数据层
     */
    private final DataBaseConfigMapper dataBaseConfigMapper;

    public boolean changeDataSource(Long dataSourceId) {
        if (CollUtil.isEmpty(initDataSourceMap)) {
            initDataSourceMap();
        }

        if (dataSource instanceof DynamicRoutingDataSource) {
            DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) this.dataSource;

            //获取所有数据源
            Map<String, DataSource> currentDataSourceMap = dynamicRoutingDataSource.getCurrentDataSources();

            //新增数据源
            String dataSourceIdStr = String.valueOf(dataSourceId);
            if (!currentDataSourceMap.containsKey(dataSourceIdStr)) {
                DataSource currentDataSource = initDataSourceMap.get(dataSourceId);
                dynamicRoutingDataSource.addDataSource(dataSourceIdStr, currentDataSource);
            }
            DynamicDataSourceContextHolder.push(dataSourceIdStr);
            return true;
        }
        return false;
    }

    /**
     * 初始化数据源映射
     */
    private void initDataSourceMap() {
        List<DataBaseConfigInfo> dataBaseConfigInfoList = dataBaseConfigMapper.selectList(
                new LambdaQueryWrapper<DataBaseConfigInfo>().eq(DataBaseConfigInfo::getEnableStatus, 0));

        if (CollUtil.isNotEmpty(dataBaseConfigInfoList)) {
            initDataSourceMap = new HashMap<>(dataBaseConfigInfoList.size());
            dataBaseConfigInfoList.forEach(s -> {
                HikariDataSource hikariDataSource = new HikariDataSource();
                hikariDataSource.setJdbcUrl(s.getDataUrl());
                hikariDataSource.setDriverClassName(s.getDriverClassName());
                hikariDataSource.setUsername(s.getUserName());
                hikariDataSource.setPassword(s.getPassword());

                initDataSourceMap.put(s.getId(), hikariDataSource);
            });
        }
    }

}
