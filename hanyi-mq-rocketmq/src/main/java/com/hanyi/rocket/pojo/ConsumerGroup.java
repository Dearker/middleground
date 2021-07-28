package com.hanyi.rocket.pojo;

import lombok.Data;

/**
 * @author wcwei@iflytek.com
 * @since 2021-07-28 14:29
 */
@Data
public class ConsumerGroup {

    /**
     * 分组
     */
    private String group;

    /**
     * 版本
     */
    private Object version;

    /**
     * 总数
     */
    private Integer count;

    /**
     * 消费类型
     */
    private Object consumeType;

    /**
     * 消息模型
     */
    private Object messageModel;

    /**
     * 消费tps
     */
    private Integer consumeTps;

    /**
     * 不同的总数
     */
    private Integer diffTotal;
}
