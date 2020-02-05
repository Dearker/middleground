package com.hanyi.demo.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.hanyi.demo.common.constant.Constant;
import com.hanyi.demo.common.utils.JedisUtil;
import com.hanyi.demo.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口幂等性校验
 * @author weiwen
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private Snowflake snowflake;

    @Override
    public String createToken() {
        String token = snowflake.nextIdStr();
        jedisUtil.set(token, token, Constant.EXPIRE_TIME_MINUTE);

        return token;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        // header中不存在token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
            // parameter中也不存在token
            if (StringUtils.isBlank(token)) {
                logger.error("checkToken方法的请求中无token");
                return false;
            }
        }

        if (!jedisUtil.exists(token)) {
            logger.error("checkToken方法的请求重复提交");
            return false;
        }

        Long del = jedisUtil.del(token);

        if(del < 0){
            logger.error("checkToken方法的删除token失败");
            return false;
        }

        return true;
    }

}
