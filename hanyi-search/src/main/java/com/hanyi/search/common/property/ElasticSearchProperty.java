package com.hanyi.search.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * es属性配置类
 * </p>
 *
 * @author wenchangwei
 * @since 9:45 上午 2020/11/1
 */
@Data
@Component
@ConfigurationProperties(prefix = "elastic-search")
public class ElasticSearchProperty {

    /**
     * 主机名称
     */
    private String hostName;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 协议
     */
    private String scheme;

}
