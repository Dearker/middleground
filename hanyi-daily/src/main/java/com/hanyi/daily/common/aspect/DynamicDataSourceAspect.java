package com.hanyi.daily.common.aspect;

import com.hanyi.daily.common.annotation.DataSource;
import com.hanyi.daily.common.handover.DataSourceType;
import com.hanyi.daily.common.enums.DataType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: middleground com.hanyi.daily.common.aspect DynamicDataSourceAspect
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-05 09:29
 * @Version: 1.0
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);


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
            logger.info("数据源已切换到Primary...");
        } else if (DataType.SECONDARY.equals(value)) {
            DataSourceType.setDataBaseType(DataType.SECONDARY);
            logger.info("数据源已切换到Secondary...");
        } else {
            //默认使用主数据库
            DataSourceType.setDataBaseType(DataType.PRIMARY);
            logger.info("使用的为默认的数据源Primary...");
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
        logger.info("数据源已清除.....");
    }

}
