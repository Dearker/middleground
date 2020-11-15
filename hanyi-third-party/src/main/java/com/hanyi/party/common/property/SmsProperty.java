package com.hanyi.party.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 短信配置类
 * </p>
 *
 * @author wenchangwei
 * @since 7:22 下午 2020/11/14
 */
@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperty {

    /**
     * 地址
     */
    private String host;

    /**
     * 路径
     */
    private String path;

    /**
     * appCode编码
     */
    private String appCode;

    /**
     * 模板id
     */
    private String tplId;

}
