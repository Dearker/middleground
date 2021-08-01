package com.hanyi.rocket.dto;

import lombok.Data;

/**
 *
 * <p>
 * 队列统计信息列表
 * </p>
 *
 * @author wenchangwei
 * @since 2021/7/31 10:56 上午
 */
@Data
public class QueueStatInfoListDTO {

    /**
     * 代理名称
     */
    private String brokerName;

    /**
     * 队列id
     */
    private Integer queueId;

    /**
     * 代理位点
     */
    private Integer brokerOffset;

    /**
     * 消费者位点
     */
    private Integer consumerOffset;

    /**
     * 客户端信息
     */
    private String clientInfo;

    /**
     * 最后一个时间戳
     */
    private Long lastTimestamp;

}
