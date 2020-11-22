package com.hanyi.canal.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * canal属性类
 * </p>
 *
 * @author wenchangwei
 * @since 7:18 下午 2020/11/22
 */
@Data
@Component
@ConfigurationProperties(prefix = "canal")
public class CanalProperty {

    /**
     * canal ip地址
     */
    private String ip;

    /**
     * canal 端口
     */
    private int port;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 过滤规则
     */
    private String subscribe;

    /**
     * canal用户名
     */
    private String username;

    /**
     * canal密码
     */
    private String passport;

}
