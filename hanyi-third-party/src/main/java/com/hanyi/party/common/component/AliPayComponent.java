package com.hanyi.party.common.component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hanyi.party.common.constant.AliPayConstant;
import com.hanyi.party.common.property.AliPayProperty;
import com.hanyi.party.vo.PayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付组件类
 * </p>
 *
 * @author wenchangwei
 * @since 8:15 下午 2020/11/19
 */
@Slf4j
@Component
public class AliPayComponent {

    @Resource
    private AliPayProperty aliPayProperty;

    public String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayProperty.getGatewayUrl(),
                aliPayProperty.getAppId(), aliPayProperty.getMerchantPrivateKey(), AliPayConstant.JSON,
                aliPayProperty.getCharset(), aliPayProperty.getAliPayPublicKey(), aliPayProperty.getSignType());

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        aliPayRequest.setReturnUrl(aliPayProperty.getReturnUrl());
        aliPayRequest.setNotifyUrl(aliPayProperty.getNotifyUrl());

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = vo.getOutTradeNo();
        //付款金额，必填
        String totalAmount = vo.getTotalAmount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        aliPayRequest.setBizContent("{\"outTradeNo\":\"" + outTradeNo + "\","
                + "\"totalAmount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + aliPayProperty.getTimeoutExpress() + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(aliPayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        log.info("支付宝的响应：{}", result);

        return result;
    }

    /**
     * 调用SDK验证签名
     *
     * @param params 请求参数
     * @return 返回是否成功
     */
    public boolean rsaCheck(Map<String, String> params) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(params, aliPayProperty.getAliPayPublicKey(),
                aliPayProperty.getCharset(), aliPayProperty.getSignType());
    }

}
