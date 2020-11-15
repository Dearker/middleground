package com.hanyi.search.common.config;

import com.hanyi.search.common.property.ElasticSearchProperty;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public RestHighLevelClient restHighLevelClient(ElasticSearchProperty elasticSearchProperty) {
        //初始化ES操作客户端
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //es账号密码
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticSearchProperty.getUserName(),
                        elasticSearchProperty.getPassword()));

        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elasticSearchProperty.getHostName(),
                                elasticSearchProperty.getPort(),
                                elasticSearchProperty.getScheme())
                ).setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }));
    }

}
