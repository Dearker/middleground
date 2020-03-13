package com.hanyi.daily.common.aspect;

import com.hanyi.daily.common.annotation.DataSource;
import com.hanyi.daily.common.handover.DataSourceType;
import com.hanyi.daily.common.enums.DataType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName: middleground com.hanyi.daily.common.aspect DynamicDataSourceAspect
 * @Author: weiwenchang
 * @Description: 动态设置数据源
 * @CreateDate: 2020-02-05 09:29
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect {

    /**
     * 拦截注解
     *
     * @param point
     * @param dataSource
     */
    @Before("@annotation(dataSource)")
    public void changeDataSource(JoinPoint point, DataSource dataSource) {
        DataType value = dataSource.value();
        if (DataType.PRIMARY.equals(value)) {
            DataSourceType.setDataBaseType(DataType.PRIMARY);
            log.info("数据源已切换到Primary...");
        } else if (DataType.SECONDARY.equals(value)) {
            DataSourceType.setDataBaseType(DataType.SECONDARY);
            log.info("数据源已切换到Secondary...");
        } else {
            //默认使用主数据库
            DataSourceType.setDataBaseType(DataType.PRIMARY);
            log.info("使用的为默认的数据源Primary...");
        }

    }

    /**
     * 清除数据源的配置
     *
     * @param point
     * @param dataSource
     */
    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        DataSourceType.clearDataBaseType();
        log.info("数据源已清除.....");
    }

}
