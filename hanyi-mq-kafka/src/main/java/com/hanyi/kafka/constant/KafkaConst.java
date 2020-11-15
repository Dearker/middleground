package com.hanyi.kafka.constant;

/**
 * <p>
 * kafka常量池
 * </p>
 *
 * @author wenchangwei
 * @since 4:53 下午 2020/5/30
 */
public interface KafkaConst {

    /**
     * 默认分区大小
     */
    Integer DEFAULT_PARTITION_NUM = 3;

    /**
     * Topic 名称
     */
    String TOPIC_TEST = "test";

    /**
     * 分组id
     */
    String GROUP_ID_DEMO = "spring-boot-demo";
}
