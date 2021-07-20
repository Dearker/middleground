package com.hanyi.clickhouse.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * <p>
 * jdbc属性
 * </p>
 *
 * @author wenchangwei
 * @date 2021/07/17
 * @since 2021/7/17 10:08 上午
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.click")
public class JdbcProperty {

    /**
     * 驱动程序类名称
     */
    private String driverClassName;

    /**
     * url
     */
    private String url;

    /**
     * 初始大小
     */
    private Integer initialSize;

    /**
     * 最大活跃
     */
    private Integer maxActive;

    /**
     * 最小空闲
     */
    private Integer minIdle;

    /**
     * 最大等带时间
     */
    private Integer maxWait;

}
