package com.hanyi.sentinel.common.config;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.fastjson.JSON;
import com.hanyi.framework.enums.ResultCode;
import com.hanyi.framework.model.response.ResponseResult;
import com.hanyi.sentinel.common.constant.SentinelConstant;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Sentinel配置类
 * </p>
 *
 * @author wenchangwei
 * @since 3:11 下午 2020/11/21
 */
@Configuration
public class SentinelConfig {

    public SentinelConfig() {
        WebCallbackManager.setUrlBlockHandler((httpServletRequest, httpServletResponse, e) -> {
            ResponseResult failure = ResponseResult.failure(ResultCode.FAILED);

            httpServletResponse.setCharacterEncoding(CharsetUtil.UTF_8);
            httpServletResponse.setContentType(SentinelConstant.CONTENT_TYPE);

            httpServletResponse.getWriter().write(JSON.toJSONString(failure));
        });
    }

}
