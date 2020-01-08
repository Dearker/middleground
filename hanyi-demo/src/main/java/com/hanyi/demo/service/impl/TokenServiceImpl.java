package com.hanyi.demo.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.hanyi.demo.common.constant.Constant;
import com.hanyi.demo.common.utils.JedisUtil;
import com.hanyi.demo.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private Snowflake snowflake;

    @Override
    public String createToken() {
        String str = snowflake.nextIdStr();
        String token = Constant.TOKEN_PREFIX + str;

        jedisUtil.set(token, token, Constant.EXPIRE_TIME_MINUTE);

        return token;
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        // header中不存在token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
            // parameter中也不存在token
            if (StringUtils.isBlank(token)) {
                //throw new ExceptionCast(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!jedisUtil.exists(token)) {
            //throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = jedisUtil.del(token);
        if (del <= 0) {
            //throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }

}
