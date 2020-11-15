package com.hanyi.party.common.component;

import com.alibaba.fastjson.JSON;
import com.hanyi.framework.utils.HttpUtils;
import com.hanyi.party.bo.OrderCode;
import com.hanyi.party.common.constant.CommonConstant;
import com.hanyi.party.common.property.SmsProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 短信组件
 *  文档地址：https://market.aliyun.com/products/56928004/cmapi023305.html?spm=5176.2020520132.101.2.594c7218EToWD8#sku=yuncode1730500007
 * </p>
 *
 * @author wenchangwei
 * @since 7:32 下午 2020/11/14
 */
@Slf4j
@Component
public class SmsComponent {

    @Resource
    private SmsProperty smsProperty;

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param code  验证码
     */
    public void sendCode(String phone, Integer code) {

        Map<String, String> queryMap = new HashMap<>(3);
        queryMap.put(CommonConstant.MOBILE, phone);
        //发送的消息，如：code:701020,code后面的为验证码信息
        queryMap.put(CommonConstant.PARAM, CommonConstant.PRE_CODE + code);
        queryMap.put(CommonConstant.TPL_ID, smsProperty.getTplId());

        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        Map<String, String> headers = Collections.singletonMap(CommonConstant.AUTHORIZATION,
                CommonConstant.APP_CODE + smsProperty.getAppCode());

        try {
            HttpResponse response = HttpUtils.doPost(smsProperty.getHost(), smsProperty.getPath(),
                    CommonConstant.POST, headers, queryMap, Collections.emptyMap());
            //获取response的body,{"return_code":"00000","order_id":"ALY1605354666847716789"}
            String toString = EntityUtils.toString(response.getEntity());
            log.info(toString);
            log.info(JSON.parseObject(toString, OrderCode.class).toString());
        } catch (Exception e) {
            log.error(" SmsComponent the sendCode have exception {}", e.getMessage());
        }
    }

}
