package com.hanyi.daily.common.component;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: middleground com.hanyi.daily.common.component DynamicDataSource
 * @Author: weiwenchang
 * @Description: 内部维护了一个名为targetDataSources的Map,并提供的setter方法用于设置数据源关键字与数据源的关系
 * @CreateDate: 2020-02-05 09:37
 * @Version: 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceType.getDataBaseType();
    }

}
