package com.hanyi.daily;

import com.hanyi.daily.common.aware.SpringAwareComponent;
import com.hanyi.daily.property.PersonProperty;
import com.hanyi.daily.service.SearchDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Map;

/**
 * @PackAge: middleground com.hanyi.daily
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-09 16:54
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTest {

    @Autowired
    private PersonProperty personProp;

    @Autowired
    private SpringAwareComponent springAwareComponent;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void bootTest() {
        System.out.println(personProp);
    }

    /**
     * 获取指定接口的所有实现类,指定的接口实现必须在spring容器中才能找到
     * <p>
     * key 为实现类的id，默认首字母小写；value为相应的实现类的对象
     */
    @Test
    public void targetInterfaceImplTest() {

        Map<String, SearchDataService> implementor = springAwareComponent.getTargetInterfaceAllImplementor(SearchDataService.class);

        implementor.forEach((k, v) -> {
            System.out.println("获取的实现名称为：" + k);
            Class<?>[] interfaces = v.getClass().getInterfaces();
            System.out.println("获取的所有的接口列表：" + Arrays.asList(interfaces));
            System.out.println("获取的实现类为：" + v);
        });
        SearchDataService searchDataService = applicationContext.getBean(SearchDataService.class);
        System.out.println("获取的对象为："+searchDataService);
        SearchDataService bean = beanFactory.getBean(SearchDataService.class);
        System.out.println("获取的bean对象为："+ bean);
    }

    /**
     * 发布事件
     */
    @Test
    public void publishEventTest() {
        springAwareComponent.applicationPublishEvent(new ApplicationEvent("无敌柯基小短腿") {
        });
    }


}
