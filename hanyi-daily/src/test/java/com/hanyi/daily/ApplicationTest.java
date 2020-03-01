package com.hanyi.daily;

import com.hanyi.daily.property.PersonProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@Slf4j
public class ApplicationTest {

    @Autowired
    private PersonProperty personProp;

    @Test
    public void bootTest(){
        System.out.println(personProp);
    }


}
