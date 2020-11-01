package com.hanyi.search.common.config;

import com.hanyi.search.common.property.ElasticSearchProperty;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * <p>
 * elasticSearch配置类
 * </p>
 *
 * @author wenchangwei
 * @since 11:02 下午 2020/10/31
 */
@Configuration
public class ElasticSearchConfig {

    @Resource
    private ElasticSearchProperty elasticSearchProperty;

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        /*builder.addHeader("Authorization", "Bearer " + TOKEN);
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory
                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));*/
        COMMON_OPTIONS = builder.build();
    }

    /**
     * es高级客户端
     *
     * @return 返回客户端对象
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elasticSearchProperty.getHostName(),
                                elasticSearchProperty.getPort(),
                                elasticSearchProperty.getScheme())));
    }

}
