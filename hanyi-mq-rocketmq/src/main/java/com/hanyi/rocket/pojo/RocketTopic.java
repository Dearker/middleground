package com.hanyi.rocket.pojo;

import lombok.Data;
import java.util.List;

/**
 * 火箭的话题
 *
 * @author wcwei@iflytek.com
 * @since 2021-07-28 11:31
 */
@Data
public class RocketTopic {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 数据
     */
    private DataDTO data;

    /**
     * 错误消息
     */
    private Object errMsg;

    @Data
    public static class DataDTO {

        /**
         * 主题列表
         */
        private List<String> topicList;

        /**
         * 代理地址
         */
        private Object brokerAddr;
    }
}
