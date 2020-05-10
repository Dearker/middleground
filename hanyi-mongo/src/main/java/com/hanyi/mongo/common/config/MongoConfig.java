package com.hanyi.mongo.common.config;

import com.hanyi.mongo.common.property.MongoSettingsProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * @PackAge: middleground com.hanyi.mongo.common.config
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 09:57
 * @Version: 1.0
 */
@Configuration
public class MongoConfig {

    @Autowired
    private MongoSettingsProperties properties;

    /**
     * MongoDB连接池
     *
     * @return
     */
    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(properties.getMaxConnectionsPerHost());
        builder.minConnectionsPerHost(properties.getMinConnectionsPerHost());

        builder.threadsAllowedToBlockForConnectionMultiplier(
                properties.getThreadsAllowedToBlockForConnectionMultiplier());
        builder.serverSelectionTimeout(properties.getServerSelectionTimeout());
        builder.maxWaitTime(properties.getMaxWaitTime());
        builder.maxConnectionIdleTime(properties.getMaxConnectionIdleTime());
        builder.maxConnectionLifeTime(properties.getMaxConnectionLifeTime());
        builder.connectTimeout(properties.getConnectTimeout());
        builder.socketTimeout(properties.getSocketTimeout());
        builder.sslEnabled(properties.getSslEnabled());
        builder.sslInvalidHostNameAllowed(properties.getSslInvalidHostNameAllowed());
        builder.alwaysUseMBeans(properties.getAlwaysUseMBeans());
        builder.heartbeatFrequency(properties.getHeartbeatFrequency());
        builder.minHeartbeatFrequency(properties.getMinHeartbeatFrequency());
        builder.heartbeatConnectTimeout(properties.getHeartbeatConnectTimeout());
        builder.heartbeatSocketTimeout(properties.getHeartbeatSocketTimeout());
        builder.localThreshold(properties.getLocalThreshold());

        MongoClientOptions mongoClientOptions = builder.build();

        ServerAddress serverAddress = new ServerAddress(properties.getHost(), properties.getPort());
        MongoClient mongoClient = new MongoClient(serverAddress, mongoClientOptions);
        return new SimpleMongoDbFactory(mongoClient, properties.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }

}
