package com.hanyi.rocket.common.component;

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
            return JSONArray.parseArray(dataJsonArray.toJSONString(), ConsumerGroup.class);
        }
        log.error("当前查询消费者分组出错");
        return Collections.emptyList();
    }

    public Integer getMessageTotalByTopic(String topic){
        String url = rocketAddress + "/message/queryMessageByTopic.query";
        Map<String,Object> paramMap = new HashMap<>(3);
        //一天的开始时间
        long epochMilli = LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        //当前时间为结束时间
        long currentTimeMillis = System.currentTimeMillis();
        paramMap.put("topic",topic);
        paramMap.put("begin",epochMilli);
        paramMap.put("end",currentTimeMillis);
        String s = HttpUtil.get(url,paramMap);

        JSONObject jsonObject = JSON.parseObject(s);
        if(Objects.equals(jsonObject.getInteger(STATUS),0)){
            JSONArray dataJsonArray = jsonObject.getJSONArray(DATA);
            return dataJsonArray.size();
        }
        log.error("当前topic获取的消息总数出错");
        return null;
    }


}
