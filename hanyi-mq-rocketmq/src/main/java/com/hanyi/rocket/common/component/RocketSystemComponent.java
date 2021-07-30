package com.hanyi.rocket.common.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanyi.rocket.pojo.ConsumerGroup;
import com.hanyi.rocket.pojo.RocketTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 火箭系统组件
 *
 * @author wcwei@iflytek.com
 * @since 2021-07-28 11:19
 */
@Slf4j
@Component
public class RocketSystemComponent {

    /**
     * 线程池的大小
     */
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 线程池执行人
     */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = ThreadUtil.newExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE);

    /**
     * 状态
     */
    private static final String STATUS = "status";

    /**
     * 数据
     */
    private static final String DATA = "data";

    @Value("${rocketmq.console.address}")
    private String rocketAddress;

    /**
     * 得到目标主题列表
     *
     * @param topicPrefix 主题的前缀
     * @return 返回主题集合
     */
    public List<String> getTargetTopicList(String topicPrefix) {
        String url = rocketAddress + "/topic/list.query";
        String s = HttpUtil.get(url);
        RocketTopic rocketTopic = JSON.parseObject(s, RocketTopic.class);
        if (Objects.equals(rocketTopic.getStatus(), 0)) {
            return Optional.ofNullable(rocketTopic.getData()).map(a -> {
                List<String> topicList = a.getTopicList();
                return topicList.stream().filter(b -> b.startsWith(topicPrefix)).collect(Collectors.toList());
            }).orElse(Collections.emptyList());
        }
        log.error("当前查询topic列表出错");
        return Collections.emptyList();
    }

    /**
     * 获取消费者组列表
     *
     * @return 返回消费者集合
     */
    public List<ConsumerGroup> getConsumerGroupList() {
        String url = rocketAddress + "/consumer/groupList.query";
        String s = HttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(s);
        if (Objects.equals(jsonObject.getInteger(STATUS), 0)) {
            JSONArray dataJsonArray = jsonObject.getJSONArray(DATA);
            return JSON.parseArray(dataJsonArray.toJSONString(), ConsumerGroup.class);
        }
        log.error("当前查询消费者分组出错");
        return Collections.emptyList();
    }

    /**
     * 得到主题下的所有消息总数
     *
     * @param topic 主题
     * @return 返回消息总共
     */
    public Integer getMessageTotalByTopic(String topic) {
        String url = rocketAddress + "/message/queryMessageByTopic.query";
        Map<String, Object> paramMap = new HashMap<>(3);
        //一天的开始时间
        long epochMilli = LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        //当前时间为结束时间
        long currentTimeMillis = System.currentTimeMillis();
        paramMap.put("topic", topic);
        paramMap.put("begin", epochMilli);
        paramMap.put("end", currentTimeMillis);
        String s = HttpUtil.get(url, paramMap);

        JSONObject jsonObject = JSON.parseObject(s);
        if (Objects.equals(jsonObject.getInteger(STATUS), 0)) {
            JSONArray dataJsonArray = jsonObject.getJSONArray(DATA);
            return dataJsonArray.size();
        }
        log.error("当前topic获取的消息总数出错，topic 为：{}", topic);
        return null;
    }

    /**
     * 根据主题前缀获取对应的主题名称和消息总数
     *
     * @param topicPrefix 主题的前缀
     * @return 返回主题和消息总数
     */
    public Map<String, Integer> getTargetTopicMessageTotal(String topicPrefix) {
        List<String> targetTopicList = this.getTargetTopicList(topicPrefix);
        if (CollUtil.isNotEmpty(targetTopicList)) {
            int size = targetTopicList.size();
            List<CompletableFuture<?>> completableFutureList = new ArrayList<>(size);
            Map<String, Integer> totalMap = new HashMap<>(size);
            targetTopicList.forEach(s -> {
                //多线程分别统计主题对应的消息总数
                CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                    Integer total = this.getMessageTotalByTopic(s);
                    if (Objects.nonNull(total)) {
                        totalMap.put(s, total);
                    }
                }, THREAD_POOL_EXECUTOR);
                completableFutureList.add(voidCompletableFuture);
            });

            //等待所有线程执行完成
            CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture<?>[0]));
            log.info("所有主题的消息统计完成");
            return totalMap;
        }
        return Collections.emptyMap();
    }

}
