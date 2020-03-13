package com.hanyi.daily.common.handover;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @ClassName: middleground com.hanyi.daily.common.component DynamicDataSource
 * @Author: weiwenchang
 * @Description: 内部维护了一个名为targetDataSources的Map,并提供的setter方法用于设置数据源关键字与数据源的关系
 * @CreateDate: 2020-02-05 09:37
 * @Version: 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceType.getDataBaseType();
    }

}
