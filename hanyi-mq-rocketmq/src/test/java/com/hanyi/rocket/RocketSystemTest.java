package com.hanyi.rocket;

import com.hanyi.rocket.common.component.RocketSystemComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 火箭系统测试
 *
 * @author wcwei@iflytek.com
 * @since 2021-07-28 11:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketSystemTest {

    @Resource
    private RocketSystemComponent rocketSystemComponent;

    /**
     * 得到测试主题列表
     */
    @Test
    public void getTopicListTest() {
        List<String> topicList = rocketSystemComponent.getTargetTopicList("test");
        System.out.println("获取到指定的topic列表：" + topicList);
    }

    /**
     * 得到消息总测试
     */
    @Test
    public void getMessageTotalTest() {
        List<String> topicList = rocketSystemComponent.getTargetTopicList("test");
        Map<String, Integer> topicMessageTotalMap = new HashMap<>(topicList.size());
        topicList.forEach(s -> {
            Integer total = rocketSystemComponent.getMessageTotalByTopic(s);
            if(Objects.nonNull(total)){
                topicMessageTotalMap.put(s, total);
            }
        });
        System.out.println("topic 对应的消息总数：" + topicMessageTotalMap);
    }

}
