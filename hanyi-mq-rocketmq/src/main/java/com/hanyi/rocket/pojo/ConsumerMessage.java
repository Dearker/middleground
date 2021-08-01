package com.hanyi.rocket.pojo;

import com.hanyi.rocket.dto.QueueStatInfoListDTO;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 消费的消息
 * </p>
 *
 * @author wenchangwei
 * @since 2021/7/31 10:51 上午
 */
@Data
public class ConsumerMessage {

    /**
     * 主题
     */
    private String topic;

    /**
     * 不同的总数
     */
    private Integer diffTotal;

    /**
     * 最后一个时间戳
     */
    private Long lastTimestamp;

    /**
     * 消息积压总数
     */
    private Integer messageBacklogTotal;

    /**
     * 队列统计信息列表
     */
    private List<QueueStatInfoListDTO> queueStatInfoList;

}
