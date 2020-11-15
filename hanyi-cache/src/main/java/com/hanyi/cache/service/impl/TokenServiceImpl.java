package com.hanyi.cache.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.hanyi.cache.common.component.JedisComponent;
import com.hanyi.cache.common.constant.TokenConstant;
import com.hanyi.cache.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口幂等性校验
 * @author weiwen
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisComponent jedisComponent;

    @Autowired
    private Snowflake snowflake;

    @Override
    public String createToken() {
        String token = snowflake.nextIdStr();
        jedisComponent.set(token, token, TokenConstant.EXPIRE_TIME_MINUTE);

        return token;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        // header中不存在token
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
            // parameter中也不存在token
            if (StrUtil.isBlank(token)) {
                log.error("checkToken方法的请求中无token");
                return false;
            }
        }

        if (!jedisComponent.exists(token)) {
            log.error("checkToken方法的请求重复提交");
            return false;
        }

        Long del = jedisComponent.del(token);

        if(del < 0){
            log.error("checkToken方法的删除token失败");
            return false;
        }

        return true;
    }

}
