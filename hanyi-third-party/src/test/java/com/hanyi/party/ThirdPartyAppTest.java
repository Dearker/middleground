package com.hanyi.party;

import cn.hutool.core.util.RandomUtil;
import com.hanyi.party.common.component.SmsComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 三方服务测试类
 * </p>
 *
 * @author wenchangwei
 * @since 5:24 下午 2020/11/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThirdPartyAppTest {

    @Autowired
    private SmsComponent smsComponent;

    @Test
    public void sendTest(){
        smsComponent.sendCode("15827530241",RandomUtil.randomInt(6));
    }

}
