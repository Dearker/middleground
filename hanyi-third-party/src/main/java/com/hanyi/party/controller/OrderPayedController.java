package com.hanyi.party.controller;

import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.hanyi.party.common.component.AliPayComponent;
import com.hanyi.party.common.constant.AliPayConstant;
import com.hanyi.party.vo.PayAsyncVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:05 下午 2020/11/19
 */
@Slf4j
@RestController
public class OrderPayedController {

    @Resource
    private AliPayComponent aliPayComponent;

    @PostMapping("/payed/notify")
    public String handleAliPayed(PayAsyncVo payAsyncVo, HttpServletRequest request) throws AlipayApiException {
        // 只要我们收到了支付宝的异步通知，告诉我们订单支付成功，我们就返回success给支付宝，支付宝就不会在进行通知

        // 验签
        //获取支付宝POST过来反馈信息
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<>(requestParams.size());
        requestParams.forEach((k, v) -> params.put(k, String.join(StrUtil.COMMA, v)));

        //调用SDK验证签名
        boolean signVerified = aliPayComponent.rsaCheck(params);

        if (signVerified) {
            // 验签成功
            // 2、修改订单状态信息
            if (AliPayConstant.TRADE_SUCCESS.equals(payAsyncVo.getTrade_status()) ||
                    AliPayConstant.TRADE_FINISHED.equals(payAsyncVo.getTrade_status())) {
                // 支付成功
                String orderSn = payAsyncVo.getOut_trade_no();
                log.info("获取的订单号：{}", orderSn);
            }

            log.info("*******验签成功******");
            return "success";
        } else {
            // 验签失败
            log.info("*******验签失败******");
            return "fail";
        }
    }

}
