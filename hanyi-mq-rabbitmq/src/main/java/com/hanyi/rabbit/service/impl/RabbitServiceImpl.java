package com.hanyi.rabbit.service.impl;

import com.hanyi.rabbit.bo.Person;
import com.hanyi.rabbit.bo.PersonInfo;
import com.hanyi.rabbit.service.RabbitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 7:13 下午 2020/11/15
 */
@Service
public class RabbitServiceImpl implements RabbitService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息条数
     *
     * @param number 条数
     * @return 返回条数
     */
    @Override
    public int sendMessage(Integer number) {
        Person person;
        PersonInfo personInfo;
        for (int i = 0; i < number; i++) {
            if (i % 2 == 0) {
                person = new Person("柯基-" + i, "123-" + i);
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", person);
            } else {
                personInfo = new PersonInfo("哈士奇-" + i, i);
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", personInfo);
            }
        }
        return number;
    }
}
