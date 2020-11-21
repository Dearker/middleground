package com.hanyi.party;

import com.alipay.api.AlipayApiException;
import com.hanyi.party.common.component.AliPayComponent;
import com.hanyi.party.vo.PayVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 *  支付测试类
 * </p>
 *
 * @author wenchangwei
 * @since 8:06 下午 2020/11/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AliPayApplyTest {

    @Autowired
    private AliPayComponent aliPayComponent;

    @Test
    public void payTest() throws AlipayApiException {
        PayVo payVo = new PayVo().setBody("111").setOutTradeNo("123").setSubject("001").setTotalAmount("300");
        String pay = aliPayComponent.pay(payVo);
        System.out.println(pay);
    }

}
