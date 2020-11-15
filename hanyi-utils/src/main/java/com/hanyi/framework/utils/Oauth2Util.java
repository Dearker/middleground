package com.hanyi.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hanyi on 2019/10/16.
 */
@Slf4j
public class Oauth2Util {

    public static Map<String, String> getJwtClaimsFromHeader(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        //取出头信息
        String authorization = request.getHeader("Authorization");
        if (StrUtil.isEmpty(authorization) || !authorization.contains("Bearer")) {
            return null;
        }
        //从Bearer 后边开始取出token
        String token = authorization.substring(7);
        Map<String, String> map = null;
        try {
            //解析jwt
            Jwt decode = JwtHelper.decode(token);
            //得到 jwt中的用户信息
            String claims = decode.getClaims();
            //将jwt转为Map
            map = JSON.parseObject(claims, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return map;
    }

}
