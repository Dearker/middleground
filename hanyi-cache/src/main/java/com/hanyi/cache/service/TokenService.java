package com.hanyi.cache.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weiwen
 */
public interface TokenService {

    String createToken();

    boolean checkToken(HttpServletRequest request);

}
