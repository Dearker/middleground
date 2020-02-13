package com.hanyi.daily;

import com.hanyi.daily.property.PersonProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @PackAge: middleground com.hanyi.daily
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-09 16:54
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTest.class);

    @Autowired
    private PersonProperty personProp;

    @Test
    public void bootTest(){
        System.out.println(personProp);
    }


}
