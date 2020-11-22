package com.hanyi.framework.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hanyi on 2019/10/15
 */
public class CookieUtil {

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, String name,
                                 String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }


    /**
     * 根据cookie名称读取cookie
     *
     * @param request
     * @param cookieNames
     * @return map<cookieName, cookieValue>
     */

    public static Map<String, String> readCookie(HttpServletRequest request, String... cookieNames) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> cookieMap = null;
        if (ArrayUtil.isNotEmpty(cookies)) {
            Set<String> cookieNameSet = CollUtil.newHashSet(cookieNames);
            cookieMap = Arrays.stream(cookies)
                    .filter(cookieNameSet::contains)
                    .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
        }
        return cookieMap;
    }
}
