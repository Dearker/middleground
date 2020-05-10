package com.hanyi.mongo.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @PackAge: middleground com.hanyi.mongo.common.config
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 09:55
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoSettingsProperties {

    private String host;
    private int port;
    private String database;

    private Integer minConnectionsPerHost = 0;
    private Integer maxConnectionsPerHost = 100;
    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;
    private Integer serverSelectionTimeout = 30000;
    private Integer maxWaitTime = 120000;
    private Integer maxConnectionIdleTime = 0;
    private Integer maxConnectionLifeTime = 0;
    private Integer connectTimeout = 10000;
    private Integer socketTimeout = 0;
    private Boolean sslEnabled = false;
    private Boolean sslInvalidHostNameAllowed = false;
    private Boolean alwaysUseMBeans = false;
    private Integer heartbeatConnectTimeout = 20000;
    private Integer heartbeatSocketTimeout = 20000;
    private Integer minHeartbeatFrequency = 500;
    private Integer heartbeatFrequency = 10000;
    private Integer localThreshold = 15;
    private String authenticationDatabase;

}
